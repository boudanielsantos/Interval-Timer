package com.example.intervaltimer.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intervaltimer.model.IntervalTimer
import com.example.intervaltimer.repository.IntervalTimerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(val intervalTimerRepository: IntervalTimerRepository) :
    ViewModel() {


    fun saveInterval(intervalTimer: IntervalTimer) =
        viewModelScope.launch() {
            intervalTimerRepository.createInterval(intervalTimer)
        }
}