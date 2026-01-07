package com.example.intervaltimer.model

import androidx.compose.runtime.MutableState

data class IntervalState(
    val name: MutableState<String>?,
    val sets: MutableState<Int>,
    val workCountMinute: MutableState<Int>,
    val workCountSecond: MutableState<Int>,
    val restCountMinute: MutableState<Int>,
    val restCountSecond: MutableState<Int>
)
