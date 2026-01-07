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
        workCountSecond = mutableStateOf(0),
        restCountMinute = mutableStateOf(0),
        restCountSecond = mutableStateOf(0)
    )
}