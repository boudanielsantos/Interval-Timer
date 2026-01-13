package com.example.intervaltimer.repository

import com.example.intervaltimer.data.IntervalTimerDao
import com.example.intervaltimer.model.IntervalTimer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IntervalTimerRepository @Inject constructor(val intervalTimerDao: IntervalTimerDao) {

    fun getAllIntervals(): Flow<List<IntervalTimer>> = intervalTimerDao.getAllIntervals()

    suspend fun createInterval(intervalTimer: IntervalTimer) =
        intervalTimerDao.createInterval(intervalTimer)

    suspend fun deleteInterval(intervalTimer: IntervalTimer) =
        intervalTimerDao.deleteInterval(intervalTimer)

    suspend fun updateInterval(intervalTimer: IntervalTimer) =
        intervalTimerDao.updateInterval(intervalTimer)
}