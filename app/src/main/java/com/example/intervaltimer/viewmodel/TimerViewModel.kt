package com.example.intervaltimer.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.intervaltimer.model.IntervalState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor() : ViewModel() {

    val intervalState = IntervalState(
        name = mutableStateOf(""),
        sets = mutableStateOf(1),
        workCountMinute = mutableStateOf(0),
        workCountSecond = mutableStateOf(1),
        restCountMinute = mutableStateOf(0),
        restCountSecond = mutableStateOf(1)
    )

    fun reset() {
        intervalState?.apply {
            sets.value = 1
            workCountMinute.value = 0
            workCountSecond.value = 1
            restCountMinute.value = 0
            restCountSecond.value = 1
        }
    }
}