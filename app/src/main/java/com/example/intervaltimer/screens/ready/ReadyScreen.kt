package com.example.intervaltimer.screens.ready

import android.media.SoundPool
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.sp
import com.example.intervaltimer.R
import kotlinx.coroutines.delay

@Composable
fun ReadyScreen(onNavigateToWorkScreen: () -> Unit) {
    ReadyContent(onNavigateToWorkScreen)
}

@Composable
fun ReadyContent(onNavigateToWorkScreen: () -> Unit) {
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
            .background(Color.Yellow)
    ) {
        var secondsRemaining by remember { mutableStateOf(5) }

        LaunchedEffect(Unit) {
            while (secondsRemaining > 0) {
                soundPool.play(tickSoundId, 1f, 1f, 1, 0, 1f)
                delay(1000L)
                secondsRemaining--
            }
            soundPool.play(doubleTickSoundId, 1f, 1f, 1, 0, 1f)
            delay(500L)
            onNavigateToWorkScreen()
        }


        Text("00: ${secondsRemaining.toString().padStart(2, '0')}", fontSize = 100.sp, fontWeight = FontWeight.Bold)
        Text("GET READY", fontSize = 45.sp, color = Color.Gray)
    }
}