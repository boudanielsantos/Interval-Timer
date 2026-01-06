package com.example.intervaltimer.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PlusMinusField(label: String, value: String, onMinus: () -> Unit, onPlus: () -> Unit) {
    Column() {

        Text(text = label, color = Color.LightGray, fontSize = 15.sp)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            IconButton(onClick = onMinus) {
                Icon(
                    Icons.Default.Remove,
                    contentDescription = "Minus Icon"
                )
            }

            Text(
                text = value,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = onPlus) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Plus Icon"
                )
            }
        }
    }


}