package com.example.intervaltimer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.intervaltimer.screens.home.HomeScreen
import com.example.intervaltimer.screens.rest.RestScreen
import com.example.intervaltimer.screens.start.StartScreen

@Composable
fun IntervalTimerNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = IntervalTimerScreens.HOME_SCREEN.name
    ) {
        composable(route = IntervalTimerScreens.START_SCREEN.name) {
            StartScreen()
        }
        composable(route = IntervalTimerScreens.REST_SCREEN.name) {
            RestScreen()
        }
        composable(route = IntervalTimerScreens.HOME_SCREEN.name) {
            HomeScreen()
        }

    }
}