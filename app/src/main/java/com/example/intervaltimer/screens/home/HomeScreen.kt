package com.example.intervaltimer.screens.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.intervaltimer.components.PlusMinusField
import com.example.intervaltimer.components.ShowAlertDialog
import com.example.intervaltimer.components.ShowToast
import com.example.intervaltimer.model.IntervalState
import com.example.intervaltimer.model.IntervalTimer
import com.example.intervaltimer.viewmodel.TimerViewModel
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    timerViewModel: TimerViewModel,
    intervalState: IntervalState,
    onNavigateToReady: () -> Unit
) {
    LaunchedEffect(Unit) {
        timerViewModel.reset()
    }
    HomeContent(intervalState = intervalState, onNavigateToReady = onNavigateToReady, homeViewModel)
}

@Composable
fun HomeContent(
    intervalState: IntervalState,
    onNavigateToReady: () -> Unit,
    homeViewModel: HomeViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 45.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .border(2.dp, Color.Blue, RoundedCornerShape(8.dp)),
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    PlusMinusField(
                        label = "SETS",
                        onMinus = {
                            if (intervalState.sets.value > 1)
                                intervalState.sets.value--
                        },
                        onPlus = {
                            intervalState.sets.value++
                        },
                        firstValueState = intervalState.sets
                    )
                    PlusMinusField(
                        label = "WORK",
                        onMinus = {
                            if (intervalState.workCountSecond.value == 0) {
                                if (intervalState.workCountMinute.value > 0) {
                                    intervalState.workCountSecond.value = 59
                                    intervalState.workCountMinute.value--
                                }
                            } else {
                                intervalState.workCountSecond.value--
                            }
                        },
                        onPlus = {
                            if (intervalState.workCountSecond.value == 59) {
                                intervalState.workCountSecond.value = 0
                                intervalState.workCountMinute.value++
                            } else {
                                intervalState.workCountSecond.value++
                            }
                        },
                        firstValueState = intervalState.workCountMinute,
                        secondValueState = intervalState.workCountSecond,
                        isTime = true
                    )
                    PlusMinusField(
                        label = "REST",
                        onMinus = {
                            if (intervalState.restCountSecond.value == 0) {
                                if (intervalState.restCountMinute.value > 0) {
                                    intervalState.restCountSecond.value = 59
                                    intervalState.restCountMinute.value--
                                }
                            } else {
                                intervalState.restCountSecond.value--
                            }
                        },
                        onPlus = {
                            if (intervalState.restCountSecond.value == 59) {
                                intervalState.restCountSecond.value = 0
                                intervalState.restCountMinute.value++
                            } else {
                                intervalState.restCountSecond.value++
                            }
                        },
                        firstValueState = intervalState.restCountMinute,
                        secondValueState = intervalState.restCountSecond,
                        isTime = true
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp, start = 5.dp, end = 5.dp)
                            .height(45.dp),
                        shape = RoundedCornerShape(8.dp),
                        onClick = { onNavigateToReady() }) {
                        Text("START")
                    }


                }
            }
            val openSaveDialogState = remember {
                mutableStateOf(false)
            }
            val workoutNameState = remember {
                mutableStateOf("")
            }

            val showToastState = remember {
                mutableStateOf(false)
            }

            if (openSaveDialogState.value) {
                ShowAlertDialog(
                    title = "Save Interval",
                    nameState = workoutNameState,
                    openDialog = openSaveDialogState,
                    hasInput = true,
                    onYesPressed = {
                        openSaveDialogState.value = false
                        val intervalTimer =
                            createIntervalTimer(workoutNameState.value, intervalState)
                        homeViewModel.saveInterval(intervalTimer)
                        workoutNameState.value = ""
                        showToastState.value = true
                    })
            }
            if (showToastState.value) {
                ShowToast("Interval Saved")
                //Reset the ShowToastState to ensure that it will always be shown in the future saving
                LaunchedEffect(true) {
                    delay(200)
                    showToastState.value = false
                }
            }

            IconButton(
                onClick = { openSaveDialogState.value = true },
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save Icon",
                    modifier = Modifier
                        .size(45.dp)
                        .padding(5.dp), tint = Color.DarkGray
                )


            }
        }

    }

}

private fun createIntervalTimer(name: String, intervalState: IntervalState): IntervalTimer {
    return IntervalTimer(
        name = name,
        workCountMinute = intervalState.workCountMinute.value,
        workCountSecond = intervalState.workCountSecond.value,
        restCountMinute = intervalState.restCountMinute.value,
        restCountSecond = intervalState.restCountSecond.value,
        sets = intervalState.sets.value
    )
}