package com.example.intervaltimer.screens.home

import androidx.lifecycle.ViewModel
import com.example.intervaltimer.repository.IntervalTimerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(intervalTimerRepository: IntervalTimerRepository) :
    ViewModel() {
}