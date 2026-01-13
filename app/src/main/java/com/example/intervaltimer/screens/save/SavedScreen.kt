package com.example.intervaltimer.screens.save

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.intervaltimer.model.IntervalTimer

@Composable
fun SavedScreen(savedIntervalViewModel: SavedIntervalViewModel) {
    val savedIntervals by savedIntervalViewModel.intervals.collectAsState()
    if (savedIntervals.data == null || savedIntervals.data!!.isEmpty()) {
        ShowNoSavedIntervals()
    } else {
        SavedContent(savedIntervals.data!!)
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
fun SavedContent(intervals: List<IntervalTimer>) {


}