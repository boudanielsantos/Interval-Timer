package com.example.intervaltimer.components

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun PlusMinusField(
    label: String,
    firstValueState: MutableState<Int>,
    secondValueState: MutableState<Int>? = null,
    onMinus: () -> Unit,
    onPlus: () -> Unit,
    isTime: Boolean = false
) {

    val isPlusPressed = remember { mutableStateOf(false) }
    val isMinusPressed = remember { mutableStateOf(false) }

    LaunchedEffect(isPlusPressed.value) {
        if (isPlusPressed.value) {
            onPlus() // Initial click
            delay(500) // Initial delay before repeating
            while (isPlusPressed.value) {
                onPlus()
                delay(100) // Speed of repetition
            }
        }
    }

    LaunchedEffect(isMinusPressed.value) {
        if (isMinusPressed.value) {
            onMinus() // Initial click
            delay(500)
            while (isMinusPressed.value) {
                onMinus()
                delay(100)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(label, fontSize = 25.sp)

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Box(
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                isMinusPressed.value = true
                                tryAwaitRelease()
                                isMinusPressed.value = false
                            }
                        )
                    }
            ) {
                Icon(
                    Icons.Default.Remove,
                    contentDescription = "Minus Icon"
                )
            }



            TextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = firstValueState.value.toString(),
                singleLine = true,
                modifier = Modifier.weight(1f),
                onValueChange = { newValue ->
                    if (newValue.isEmpty()) {
                        firstValueState.value = if (!isTime) 1 else 0
                    } else {
                        val filtered = newValue.filter { it.isDigit() }
                        filtered.toIntOrNull()?.let {
                            firstValueState.value = it
                        }
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                ),
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 45.sp
                )
            )

            //Minute TextField
            if (isTime) {
                Text(":", fontSize = 45.sp, fontWeight = FontWeight.Bold)
                TextField(
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    value = secondValueState!!.value.toString(),
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    onValueChange = { newValue ->
                        if (newValue.isEmpty()) {
                            secondValueState.value = 0
                        } else {
                            val filtered = newValue.filter { it.isDigit() }
                            filtered.toIntOrNull()?.let {
                                secondValueState.value = it
                            }
                        }

                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                    ),
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center,
                        fontSize = 45.sp
                    )
                )
            }
            Box(
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                isPlusPressed.value = true
                                tryAwaitRelease()
                                isPlusPressed.value = false
                            }
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Plus Icon"
                )
            }

        }
    }


}