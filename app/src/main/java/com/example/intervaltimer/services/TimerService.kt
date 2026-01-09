package com.example.intervaltimer.services

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import com.example.utils.Utils.formatMillis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerService : Service() {
    override fun onBind(p0: Intent?): IBinder? = null

    private var timerJob: Job? = null
    private val serviceScope = CoroutineScope(Dispatchers.Main + Job())


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                val duration = intent.getLongExtra("DURATION_MS", 0L)
                startTimer(duration)
            }

            ACTION_PAUSE -> pauseTimer()
            ACTION_RESUME -> resumeTimer()
        }
        return START_STICKY
    }

    private fun startTimer(duration: Long) {
        timeLeft.longValue = duration
        isPaused.value = false
        isRunning.value = true
        runTimerLoop()
    }

    private fun pauseTimer() {
        isPaused.value = true
        timerJob?.cancel()
        updateNotification()
    }

    private fun resumeTimer() {
        isPaused.value = false
        runTimerLoop()
    }

    private fun runTimerLoop() {
        timerJob?.cancel()
        timerJob = serviceScope.launch {
            while (timeLeft.longValue > 0) {
                updateNotification()
                delay(1000)
                timeLeft.longValue -= 1000
            }
            isRunning.value = false
            stopSelf()
        }
    }

    private fun updateNotification() {
        val title = if (isPaused.value) "Timer Paused" else "Workout Active"
        val actionText = if (isPaused.value) "Resume" else "Pause"
        val actionIntent = if (isPaused.value) ACTION_RESUME else ACTION_PAUSE
        val pendingIntent = PendingIntent.getService(
            this, 0,
            Intent(this, TimerService::class.java).apply { action = actionIntent },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText("Remaining: ${formatMillis(timeLeft.longValue)}")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .addAction(0, actionText, pendingIntent) // The Pause/Resume Button
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .build()

        startForeground(1, notification)

    }

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }

    companion object {
        const val CHANNEL_ID = "timer_channel"
        const val ACTION_START = "ACTION_START"
        const val ACTION_PAUSE = "ACTION_PAUSE"
        const val ACTION_RESUME = "ACTION_RESUME"


        val timeLeft = mutableLongStateOf(0L)
        val isRunning = mutableStateOf(false)
        val isPaused = mutableStateOf(false)
    }
}