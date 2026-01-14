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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intervaltimer.components.ShowAlertDialog
import com.example.intervaltimer.components.ShowToast
import com.example.intervaltimer.model.IntervalTimer
import com.example.intervaltimer.viewmodel.TimerViewModel
import kotlinx.coroutines.delay
import java.util.Timer

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
private fun IntervalItemDropDownMenu(
    showMenu: MutableState<Boolean>,
    openDeleteDialogState: MutableState<Boolean>,
    isEditMode: MutableState<Boolean>
) {
    DropdownMenu(
        expanded = showMenu.value,
        onDismissRequest = { showMenu.value = false }
    ) {
        DropdownMenuItem(
            text = { Text("Edit") },
            onClick = {
                showMenu.value = false
                isEditMode.value = true
            }
        )
        DropdownMenuItem(
            text = { Text("Delete") },
            onClick = {
                showMenu.value = false
                openDeleteDialogState.value = true
            }
        )
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
    val showMenu = remember { mutableStateOf(false) }
    val openDeleteDialogState = remember {
        mutableStateOf(false)
    }
    val showToastState = remember {
        mutableStateOf(false)
    }
    val isEditMode = remember {
        mutableStateOf(false)
    }

    var editedName by remember(interval, isEditMode.value) { mutableStateOf(interval.name) }
    var editedWorkMinutes by remember(
        interval,
        isEditMode.value
    ) { mutableStateOf(interval.workCountMinute.toString()) }
    var editedWorkSeconds by remember(
        interval,
        isEditMode.value
    ) { mutableStateOf(interval.workCountSecond.toString()) }
    var editedRestMinutes by remember(
        interval,
        isEditMode.value
    ) { mutableStateOf(interval.restCountMinute.toString()) }
    var editedRestSeconds by remember(
        interval,
        isEditMode.value
    ) { mutableStateOf(interval.restCountSecond.toString()) }
    var editedSets by remember(
        interval,
        isEditMode.value
    ) { mutableStateOf(interval.sets.toString()) }



    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .size(if (!isEditMode.value) 200.dp else 300.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                if (!isEditMode.value) {
                    IconButton(onClick = { showMenu.value = !showMenu.value }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More Options"
                        )
                    }
                    IntervalItemDropDownMenu(showMenu, openDeleteDialogState, isEditMode)
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


            Column() {
                if (isEditMode.value) {
                    EditableIntervalItem(
                        name = editedName,
                        onNameChange = { editedName = it },
                        workMinutes = editedWorkMinutes,
                        onWorkMinutesChange = { editedWorkMinutes = it },
                        workSeconds = editedWorkSeconds,
                        onWorkSecondsChange = { editedWorkSeconds = it },
                        restMinutes = editedRestMinutes,
                        onRestMinutesChange = { editedRestMinutes = it },
                        restSeconds = editedRestSeconds,
                        onRestSecondsChange = { editedRestSeconds = it },
                        sets = editedSets,
                        onSetsChange = { editedSets = it }
                    )
                } else {
                    UneditableIntervalItem(interval)
                }
            }
            if (!isEditMode.value) {
                StartButton(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    interval = interval,
                    timerViewModel = timerViewModel,
                    onNavigateToReady = onNavigateToReady
                )
            } else {
                SaveButton(modifier = Modifier.align(Alignment.BottomEnd)) {
                    val updatedInterval = interval.copy(
                        name = editedName,
                        workCountMinute = editedWorkMinutes.toIntOrNull() ?: 0,
                        workCountSecond = editedWorkSeconds.toIntOrNull() ?: 0,
                        restCountMinute = editedRestMinutes.toIntOrNull() ?: 0,
                        restCountSecond = editedRestSeconds.toIntOrNull() ?: 0,
                        sets = editedSets.toIntOrNull() ?: 1
                    )
                    savedIntervalViewModel.updateInterval(updatedInterval)
                    isEditMode.value = false
                }
            }
        }


    }
}

@Composable
fun StartButton(
    modifier: Modifier,
    interval: IntervalTimer,
    timerViewModel: TimerViewModel,
    onNavigateToReady: () -> Unit
) {
    Row(
        modifier = modifier
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

}

@Composable
fun SaveButton(modifier: Modifier = Modifier, onSave: () -> Unit) {
    Row(
        modifier = modifier
            .clickable { onSave() }
            .padding(bottom = 8.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onSave() }) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Save Interval Icon"
            )
        }
        Text("Save")
    }
}

@Composable
fun EditableIntervalItem(
    name: String, onNameChange: (String) -> Unit,
    workMinutes: String, onWorkMinutesChange: (String) -> Unit,
    workSeconds: String, onWorkSecondsChange: (String) -> Unit,
    restMinutes: String, onRestMinutesChange: (String) -> Unit,
    restSeconds: String, onRestSecondsChange: (String) -> Unit,
    sets: String, onSetsChange: (String) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        TextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = sets,
            onValueChange = onSetsChange,
            label = { Text("Sets") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextField(
                value = workMinutes,
                onValueChange = onWorkMinutesChange,
                label = { Text("Work (M)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            TextField(
                value = workSeconds,
                onValueChange = onWorkSecondsChange,
                label = { Text("Work (S)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextField(
                value = restMinutes,
                onValueChange = onRestMinutesChange,
                label = { Text("Rest (M)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            TextField(
                value = restSeconds,
                onValueChange = onRestSecondsChange,
                label = { Text("Rest (S)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

    }
}

@Composable
fun UneditableIntervalItem(interval: IntervalTimer) {
    Text(
        interval.name,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(8.dp)
    )

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