package com.example.intervaltimer

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.intervaltimer.data.IntervalTimerDao
import com.example.intervaltimer.model.IntervalTimer

@Database(entities = [IntervalTimer::class], version = 1)
abstract class IntervalTimerDatabase : RoomDatabase() {

    abstract fun intervalTimerDao(): IntervalTimerDao
}