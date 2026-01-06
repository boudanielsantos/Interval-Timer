package com.example.intervaltimer.screens.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.intervaltimer.components.PlusMinusField

@Composable
fun HomeScreen(onNavigateToStart: () -> Unit) {
    HomeContent()
}

@Composable
fun HomeContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 45.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Surface(
            modifier = Modifier
                .size(450.dp)
                .fillMaxWidth()
                .border(2.dp, Color.Blue, RoundedCornerShape(8.dp)), // Border applied here
            color = Color.LightGray,
            shape = RoundedCornerShape(8.dp) // Shape for the surface itself
        ) {


            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                PlusMinusField(label = "SETS", onMinus = {}, onPlus = {}, value = "0")
                PlusMinusField(label = "WORK", onMinus = {}, onPlus = {}, value = "0")
                PlusMinusField(label = "REST", onMinus = {}, onPlus = {}, value = "0")
            }
        }
    }

}
