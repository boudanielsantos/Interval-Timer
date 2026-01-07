package com.example.intervaltimer.screens.work

import android.media.SoundPool
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.intervaltimer.R
import com.example.intervaltimer.model.IntervalState
import kotlinx.coroutines.delay

@Composable
fun WorkScreen(intervalState: IntervalState) {
    WorkContent(intervalState)
}

@Composable
fun WorkContent(intervalState: IntervalState) {
    val context = LocalContext.current
    val soundPool = remember {
        SoundPool.Builder().setMaxStreams(2).build()
    }

    val tickSoundId = remember { soundPool.load(context, R.raw.single_tick, 1) }
    val doubleTickSoundId = remember { soundPool.load(context, R.raw.double_tick, 1) }


    DisposableEffect(Unit) {
        onDispose { soundPool.release() }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green)
    ) {
    
        var minuteRemaining by remember { mutableStateOf(intervalState.workCountMinute) }
        var secondsRemaining by remember { mutableStateOf(intervalState.workCountSecond) }

        LaunchedEffect(Unit) {

            while (minuteRemaining.value > 0 || secondsRemaining.value > 0) {

                delay(1000L)
                if (secondsRemaining.value > 0) {
                    secondsRemaining.value--
                    //Play sound during the last 3 seconds of the workout
                    if (minuteRemaining.value == 0 && secondsRemaining.value < 4) {
                        soundPool.play(tickSoundId, 1f, 1f, 1, 0, 1f)
                    }
                } else if (minuteRemaining.value > 0) {
                    minuteRemaining.value--
                    secondsRemaining.value = 59
                }


            }
            soundPool.play(doubleTickSoundId, 1f, 1f, 1, 0, 1f)
            delay(500L)
        }
        val timeText = "%02d:%02d".format(minuteRemaining.value, secondsRemaining.value)

        Text(intervalState.sets.value.toString(), fontSize = 45.sp)
        Text(text = timeText, fontSize = 100.sp, fontWeight = FontWeight.Bold)
        Text("WORK", fontSize = 45.sp, color = Color.Gray)


    }
}