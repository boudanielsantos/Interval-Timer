package com.example.intervaltimer.screens.work

import android.app.NotificationChannel
import android.app.NotificationManager
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
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

@Composable
fun WorkScreen(intervalState: IntervalState, onNavigateToRest: () -> Unit) {
    WorkContent(intervalState, onNavigateToRest)
}

@Composable
fun WorkContent(intervalState: IntervalState, onNavigateToRest: () -> Unit) {
    val context = LocalContext.current
    val remainingTime by TimerService.timeLeft
    val isRunning by TimerService.isRunning
    val isPaused by TimerService.isPaused
    val totalTimeMillis = (intervalState.workCountMinute.value * 60000L) +
            (intervalState.workCountSecond.value * 1000L)
    var hasTimerStarted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        // 1. Create Notification Channel
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                TimerService.CHANNEL_ID,
                "Workout Timer",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        // 2. Start Service Automatically
        if (!isRunning) {
            val intent = Intent(context, TimerService::class.java).apply {
                action = TimerService.ACTION_START
                putExtra("DURATION_MS", totalTimeMillis)
            }
            context.startForegroundService(intent)
        }
    }

    LaunchedEffect(remainingTime) {

        if (remainingTime > 0L) {
            hasTimerStarted = true
        }
        if (remainingTime <= 0L && hasTimerStarted) {
            delay(400)
            onNavigateToRest()
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Green)
        ) {

            Text(intervalState.sets.value.toString(), fontSize = 45.sp)
            Text(
                text = formatMillis(remainingTime),
                fontSize = 100.sp,
                fontWeight = FontWeight.Bold
            )
            Text("WORK", fontSize = 45.sp, color = Color.Gray)


        }
        FilledIconButton(
            shape = CircleShape,
            onClick = {
                val action = if (isPaused) TimerService.ACTION_RESUME else TimerService.ACTION_PAUSE
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
                imageVector = if (!isPaused) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = "Pause Icon"
            )
        }

        Button(
            onClick = {},
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(18.dp)
        ) {
            Text("Skip", fontSize = 25.sp, fontWeight = FontWeight.Bold)
        }


    }
}

