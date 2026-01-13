package com.example.intervaltimer.screens.save

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intervaltimer.data.DataOrException
import com.example.intervaltimer.model.IntervalTimer
import com.example.intervaltimer.repository.IntervalTimerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@HiltViewModel
class SavedIntervalViewModel @Inject constructor(val intervalTimerRepository: IntervalTimerRepository) :
    ViewModel() {


    private val _intervals =
        MutableStateFlow<DataOrException<List<IntervalTimer>, Boolean, Exception>>(
            DataOrException(
                listOf(), true, Exception("")
            )
        )
    val intervals = _intervals.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                intervalTimerRepository.getAllIntervals().distinctUntilChanged()
                    .collect { intervalsFromDb ->
                        _intervals.value = _intervals.value.copy(
                            data = intervalsFromDb, loading = false, exception = null
                        )
                    }
            } catch (e: Exception) {
                _intervals.value = _intervals.value.copy(loading = false, exception = e)
            }
        }
    }

    fun deleteInterval(intervalTimer: IntervalTimer) = viewModelScope.launch {
        intervalTimerRepository.deleteInterval(intervalTimer)
    }
}