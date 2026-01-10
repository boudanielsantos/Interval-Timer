package com.example.intervaltimer.screens.finish

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FinishScreen(onNavigateToWork: () -> Unit) {
    FinishContent(onNavigateToWork = onNavigateToWork)
}


@Preview
@Composable
fun FinishContent(onNavigateToWork: () -> Unit = {}) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {


            Text(
                maxLines = 2,
                textAlign = TextAlign.Center,
                text = "Workout Complete!", fontWeight = FontWeight.ExtraBold,
                fontSize = 55.sp,
                lineHeight = 60.sp
            )
        }

        IconButton(
            modifier = Modifier
                .padding(6.dp)
                .align(Alignment.BottomCenter), onClick = { onNavigateToWork() }) {
            Icon(
                imageVector = Icons.Default.Refresh, contentDescription = "Refresh Icon",
                modifier = Modifier.size(100.dp)
            )

        }

    }


}