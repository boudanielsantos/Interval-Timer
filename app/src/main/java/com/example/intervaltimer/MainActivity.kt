package com.example.intervaltimer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.intervaltimer.navigation.BottomNavigationBar
import com.example.intervaltimer.navigation.IntervalTimerScreens
import com.example.intervaltimer.screens.finish.FinishScreen
import com.example.intervaltimer.screens.home.HomeScreen
import com.example.intervaltimer.screens.ready.ReadyScreen
import com.example.intervaltimer.screens.rest.RestScreen
import com.example.intervaltimer.screens.save.SavedScreen
import com.example.intervaltimer.screens.work.WorkScreen
import com.example.intervaltimer.ui.theme.IntervalTimerTheme
import com.example.intervaltimer.viewmodel.TimerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntervalTimerTheme {
                val context = LocalContext.current
                val permissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) { isGranted ->
                    if (isGranted) {
                        // Permission granted! Notifications will now show.
                    } else {
                        // Permission denied. You might want to show a UI message
                        // explaining why the notification is needed for the timer.
                    }
                }
                LaunchedEffect(Unit) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val hasPermission = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED

                        if (!hasPermission) {
                            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }
                }

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
            val viewModel: TimerViewModel = hiltViewModel()
            var totalSets = remember { viewModel.intervalState.sets.value }

            NavHost(
                navController = navController,
                startDestination = IntervalTimerScreens.HOME_SCREEN.name
            ) {
                composable(route = IntervalTimerScreens.WORK_SCREEN.name) {

                    WorkScreen(
                        intervalState = viewModel.intervalState,
                        onNavigateToRest = { navController.navigate(IntervalTimerScreens.REST_SCREEN.name) },
                        totalSets = totalSets
                    )
                }
                composable(route = IntervalTimerScreens.REST_SCREEN.name) {
                    RestScreen(
                        onNavigateToWorkScreen = {
                            navController.navigate(
                                IntervalTimerScreens.WORK_SCREEN.name
                            )
                        }, onNavigateToFinishScreen = {
                            navController.navigate(
                                IntervalTimerScreens.FINISH_SCREEN.name
                            )

                        },
                        intervalState = viewModel.intervalState,
                        totalSets = totalSets
                    )
                }
                composable(route = IntervalTimerScreens.HOME_SCREEN.name) {
                    HomeScreen(
                        intervalState = viewModel.intervalState,
                        onNavigateToReady = {
                            totalSets = viewModel.intervalState.sets.value
                            navController.navigate(IntervalTimerScreens.READY_SCREEN.name)
                        })
                }
                composable(route = IntervalTimerScreens.SAVED_SCREEN.name) {
                    SavedScreen()
                }
                composable(route = IntervalTimerScreens.READY_SCREEN.name) {
                    ReadyScreen(onNavigateToWorkScreen = {
                        navController.navigate(
                            IntervalTimerScreens.WORK_SCREEN.name
                        )
                    })
                }

                composable(route = IntervalTimerScreens.FINISH_SCREEN.name) {
                    FinishScreen(
                        onNavigateToWork = {
                            navController.navigate(IntervalTimerScreens.WORK_SCREEN.name)
                        }
                    )
                }
            }
        }

    }
}