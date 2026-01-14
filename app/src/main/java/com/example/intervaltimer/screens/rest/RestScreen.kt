package com.example.intervaltimer.screens.rest

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.intervaltimer.model.IntervalState
import com.example.intervaltimer.services.TimerService
import com.example.utils.Utils.formatMillis
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RestScreen(
    intervalState: IntervalState,
    onNavigateToWorkScreen: () -> Unit,
    onNavigateToFinishScreen: () -> Unit,
    totalSets: Int
) {
    val context = LocalContext.current
    val remainingTime by TimerService.timeLeft
    val isRunning by TimerService.isRunning
    var wasRunning by remember { mutableStateOf(isRunning) }
    val skipClicked = remember { mutableStateOf(false) }
    val isPaused = TimerService.isPaused
    LaunchedEffect(key1 = Unit) {
        val totalRestTime =
            (intervalState.restCountMinute.value * 60 + intervalState.restCountSecond.value) * 1000L
        if (totalRestTime > 0) {
            delay(250)
            startTimerService(context, totalRestTime)
        }
    }

    LaunchedEffect(isRunning) {
        //Should only run when timer is completed naturally without skipping
        if (wasRunning && !isRunning && remainingTime <= 0 && !skipClicked.value) {
            stopTimerService(context)

            if (intervalState.sets.value > 1) {
                onNavigateToWorkScreen()
                delay(150)
                intervalState.sets.value--
            } else {
                onNavigateToFinishScreen()
            }
        }
        wasRunning = isRunning
    }
    RestContent(
        remainingTime = remainingTime,
        setsRemaining = intervalState.sets.value,
        totalSets = totalSets,
        intervalState = intervalState,
        onNavigateToWorkScreen = onNavigateToWorkScreen,
        onNavigateToFinishScreen = onNavigateToFinishScreen,
        context = context,
        skipClicked = skipClicked,
        isPaused = isPaused
    )
}

@Composable
fun RestContent(
    remainingTime: Long,
    setsRemaining: Int,
    intervalState: IntervalState,
    totalSets: Int,
    onNavigateToWorkScreen: () -> Unit = {},
    onNavigateToFinishScreen: () -> Unit = {},
    context: Context,
    skipClicked: MutableState<Boolean>,
    isPaused: MutableState<Boolean>

) {
    val scope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
        ) {
            Text("${setsRemaining}/${totalSets}", fontSize = 45.sp)

            Text(
                text = formatMillis(remainingTime),
                fontSize = 100.sp,
                fontWeight = FontWeight.Bold
            )

            Text(text = "REST", fontSize = 45.sp, color = Color.LightGray)
        }
        FilledIconButton(
            shape = CircleShape,
            onClick = {
                val action = if (isPaused.value) TimerService.ACTION_RESUME else TimerService.ACTION_PAUSE
                val intent = Intent(context, TimerService::class.java).apply {
                    this.action = action
                }
                context.startService(intent)

            },
            modifier = Modifier
                .size(90.dp)
                .align(Alignment.BottomCenter)
                .padding(12.dp)
        ) {
            Icon(
                imageVector = if (!isPaused.value) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = "Pause Icon"
            )
        }
        Button(
            onClick = {
                skipClicked.value = true
                stopTimerService(context)
                if (setsRemaining > 1) {
                    onNavigateToWorkScreen()
                    scope.launch {
                        delay(150)
                        intervalState.sets.value--
                    }

                } else {
                    onNavigateToFinishScreen()
                }

            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(18.dp)
        ) {
            Text("Skip", fontSize = 25.sp, fontWeight = FontWeight.Bold)
        }
    }


}

private fun startTimerService(context: Context, durationMs: Long) {
    val intent = Intent(context, TimerService::class.java).apply {
        action = TimerService.ACTION_START
        putExtra("DURATION_MS", durationMs)
    }
    context.startForegroundService(intent)
}

private fun stopTimerService(context: Context) {
    val intent = Intent(context, TimerService::class.java).apply {
        action = TimerService.ACTION_STOP
    }
    context.startService(intent)
}