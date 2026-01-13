package com.example.intervaltimer.screens.save

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intervaltimer.components.ShowAlertDialog
import com.example.intervaltimer.components.ShowToast
import com.example.intervaltimer.model.IntervalTimer
import com.example.intervaltimer.viewmodel.TimerViewModel
import kotlinx.coroutines.delay

@Composable
fun SavedScreen(
    savedIntervalViewModel: SavedIntervalViewModel,
    onNavigateToReady: () -> Unit,
    timerViewModel: TimerViewModel
) {
    val savedIntervals by savedIntervalViewModel.intervals.collectAsState()
    if (savedIntervals.data == null || savedIntervals.data!!.isEmpty()) {
        ShowNoSavedIntervals()
    } else {
        SavedContent(
            savedIntervals.data!!,
            onNavigateToReady,
            timerViewModel,
            savedIntervalViewModel
        )
    }

}

@Composable
fun ShowNoSavedIntervals() {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No intervals saved", fontSize = 20.sp,
            color = Color.LightGray.copy(alpha = 0.8f)
        )
    }
}

@Composable
fun SavedContent(
    intervals: List<IntervalTimer>,
    onNavigateToReady: () -> Unit,
    timerViewModel: TimerViewModel,
    savedIntervalViewModel: SavedIntervalViewModel
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items = intervals) { interval ->
            IntervalItem(interval, onNavigateToReady, timerViewModel, savedIntervalViewModel)
        }
    }

}

@Composable
fun IntervalItem(
    interval: IntervalTimer = IntervalTimer(
        name = "Interval 1",
        workCountMinute = 20,
        workCountSecond = 30,
        restCountMinute = 20,
        restCountSecond = 30,
        sets = 5
    ),
    onNavigateToReady: () -> Unit,
    timerViewModel: TimerViewModel,
    savedIntervalViewModel: SavedIntervalViewModel
) {
    var showMenu by remember { mutableStateOf(false) }
    val openDeleteDialogState = remember {
        mutableStateOf(false)
    }
    val showToastState = remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .size(200.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More Options"
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Edit") },
                        onClick = { }
                    )
                    DropdownMenuItem(
                        text = { Text("Delete") },
                        onClick = {
                            openDeleteDialogState.value = true
                        }
                    )
                }
            }
            if (openDeleteDialogState.value) {
                ShowAlertDialog(
                    title = "Delete",
                    message = "Are you sure you want to delete the interval?",
                    openDialog = openDeleteDialogState,
                    onYesPressed = {
                        openDeleteDialogState.value = false
                        savedIntervalViewModel.deleteInterval(interval)
                    }
                )
            }
            if (showToastState.value) {
                ShowToast("Interval Saved")
                //Reset the ShowToastState to ensure that it will always be shown in the future saving
                LaunchedEffect(true) {
                    delay(200)
                    showToastState.value = false
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clickable {
                        timerViewModel.setIntervalValues(
                            workMinutes = interval.workCountMinute,
                            workSeconds = interval.workCountSecond,
                            restMinutes = interval.restCountMinute,
                            restSeconds = interval.restCountSecond,
                            workSets = interval.sets
                        )
                        onNavigateToReady()
                    }
                    .padding(bottom = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    timerViewModel.setIntervalValues(
                        workMinutes = interval.workCountMinute,
                        workSeconds = interval.workCountSecond,
                        restMinutes = interval.restCountMinute,
                        restSeconds = interval.restCountSecond,
                        workSets = interval.sets
                    )
                    onNavigateToReady()
                }) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play Saved Workout Icon"
                    )
                }
                Text("Start")
            }
            Column() {
                Text(interval.name, style = MaterialTheme.typography.headlineLarge)

                Text(
                    "Sets :     ${interval.sets}x", style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    "WORK :  ${interval.workCountMinute} : ${interval.workCountSecond}",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    "REST :    ${interval.restCountMinute} : ${interval.restCountSecond}",
                    style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(8.dp)
                )


            }
        }


    }
}