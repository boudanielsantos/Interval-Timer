package com.example.intervaltimer.screens.home

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Interval
import androidx.compose.ui.unit.dp
import com.example.intervaltimer.components.PlusMinusField
import com.example.intervaltimer.model.IntervalState
import com.example.intervaltimer.viewmodel.TimerViewModel
import java.util.logging.Logger

@Composable
fun HomeScreen(
    timerViewModel: TimerViewModel,
    intervalState: IntervalState,
    onNavigateToReady: () -> Unit
) {
    DisposableEffect(key1 = Unit) {
        onDispose {
            timerViewModel.reset()
        }
    }
    HomeContent(intervalState = intervalState, onNavigateToReady = onNavigateToReady)
}

@Composable
fun HomeContent(intervalState: IntervalState, onNavigateToReady: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 45.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Log.i("TAG,", "HELLO THIS IS INTERVALSTATEVALUES ${intervalState.toString()}")
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
    }

}
