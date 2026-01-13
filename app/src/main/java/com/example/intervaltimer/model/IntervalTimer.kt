package com.example.intervaltimer.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "interval_timer")
data class IntervalTimer(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String,
    val workCountMinute: Int,
    val workCountSecond: Int,
    val restCountMinute: Int,
    val restCountSecond: Int,
    val sets: Int
) {
}