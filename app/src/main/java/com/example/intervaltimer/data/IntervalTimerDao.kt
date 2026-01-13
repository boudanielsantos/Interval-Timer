package com.example.intervaltimer.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.intervaltimer.model.IntervalTimer
import kotlinx.coroutines.flow.Flow

@Dao
interface IntervalTimerDao {

    @Query("SELECT * from interval_timer")
    fun getAllIntervals(): Flow<List<IntervalTimer>>

    @Insert
    fun createInterval(intervalTimer: IntervalTimer)

    @Delete
    fun deleteInterval(intervalTimer: IntervalTimer)

    @Update
    fun updateInterval(intervalTimer: IntervalTimer)
}