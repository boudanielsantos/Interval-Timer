package com.example.utils

object Utils {
    fun formatMillis(ms: Long): String {
        val totalSecs = ms / 1000
        return "%02d:%02d".format(totalSecs / 60, totalSecs % 60)
    }
}