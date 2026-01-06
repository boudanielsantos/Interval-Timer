package com.example.intervaltimer.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem(IntervalTimerScreens.HOME_SCREEN.name, Icons.Filled.Home, "Home")
    object Saved :
        BottomNavItem(IntervalTimerScreens.SAVED_SCREEN.name, Icons.Filled.Bookmark, "Saved")
}