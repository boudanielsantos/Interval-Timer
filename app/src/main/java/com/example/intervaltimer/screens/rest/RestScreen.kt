package com.example.intervaltimer.screens.rest

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@Composable
fun RestScreen(
    intervalState: IntervalState,
    onNavigateToWorkScreen: () -> Unit,
    onNavigateToFinishScreen: () -> Unit
) {
    val context = LocalContext.current
    val remainingTime by TimerService.timeLeft
    val isRunning by TimerService.isRunning
    var wasRunning by remember { mutableStateOf(isRunning) }
    LaunchedEffect(key1 = Unit) {
        val totalRestTime =
            (intervalState.restCountMinute.value * 60 + intervalState.restCountSecond.value) * 1000L
        if (totalRestTime > 0) {
            startTimerService(context, totalRestTime)
        }
    }

    LaunchedEffect(isRunning) {
        if (wasRunning && !isRunning && remainingTime <= 0) {
            if (intervalState.sets.value > 1) {
                intervalState.sets.value--
                onNavigateToWorkScreen()
            } else {
                onNavigateToFinishScreen()
            }
        }
        wasRunning = isRunning
    }
    RestContent(remainingTime, intervalState.sets.value)
}

@Composable
fun RestContent(
    remainingTime: Long,
    setsRemaining: Int
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
        Text(text = "Set $setsRemaining", fontSize = 45.sp, color = Color.White)

        Text(
            text = formatMillis(remainingTime),
            fontSize = 100.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(text = "REST", fontSize = 45.sp, color = Color.LightGray)
    }
}

private fun startTimerService(context: Context, durationMs: Long) {
    val intent = Intent(context, TimerService::class.java).apply {
        action = TimerService.ACTION_START
        putExtra("DURATION_MS", durationMs)
    }
    context.startService(intent)
}