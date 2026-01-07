package com.example.intervaltimer.screens.ready

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun ReadyScreen() {
    ReadyContent()
}

@Preview
@Composable
fun ReadyContent() {
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
                delay(1000L)
                secondsRemaining--
            }
        }

        Text("GET READY", fontSize = 45.sp, color = Color.LightGray)

        Text("00: ${secondsRemaining.toString().padStart(2, '0')}", fontSize = 45.sp)
    }
}