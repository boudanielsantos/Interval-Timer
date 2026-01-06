package com.example.intervaltimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.intervaltimer.navigation.BottomNavigationBar
import com.example.intervaltimer.navigation.IntervalTimerScreens
import com.example.intervaltimer.screens.home.HomeScreen
import com.example.intervaltimer.screens.rest.RestScreen
import com.example.intervaltimer.screens.save.SavedScreen
import com.example.intervaltimer.screens.start.StartScreen
import com.example.intervaltimer.ui.theme.IntervalTimerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntervalTimerTheme {
                IntervalTimerContent()
            }
        }
    }
}


@Composable
fun IntervalTimerContent() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
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
                    HomeScreen(onNavigateToStart = { navController.navigate(IntervalTimerScreens.START_SCREEN.name) })
                }
                composable(route = IntervalTimerScreens.SAVED_SCREEN.name) {
                    SavedScreen()
                }
            }
        }

    }
}