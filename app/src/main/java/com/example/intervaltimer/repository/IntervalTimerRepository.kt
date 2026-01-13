package com.example.intervaltimer.repository

import com.example.intervaltimer.data.IntervalTimerDao
import com.example.intervaltimer.model.IntervalTimer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IntervalTimerRepository @Inject constructor(val intervalTimerDao: IntervalTimerDao) {

    fun getAllIntervals(): Flow<List<IntervalTimer>> = intervalTimerDao.getAllIntervals()

    fun createInterval(intervalTimer: IntervalTimer) =
        intervalTimerDao.createInterval(intervalTimer)

    fun deleteInterval(intervalTimer: IntervalTimer) =
        intervalTimerDao.deleteInterval(intervalTimer)

    fun updateInterval(intervalTimer: IntervalTimer) =
        intervalTimerDao.updateInterval(intervalTimer)
}