package com.example.intervaltimer.navigation

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Preview
@Composable
fun BottomNavigationBar(navController: NavController = rememberNavController()) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Saved
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Log.i("TAG", "ETO LABEL ${items.get(0).label} ETO ICON ${items.get(0).icon}")
    NavigationBar {
        items.forEach {
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Gray, // Color when selected
                    selectedTextColor = MaterialTheme.colorScheme.primary, // Text color when selected
                    unselectedIconColor = Color.Gray, // Color when unselected
                    unselectedTextColor = Color.Gray, // Text color when unselected
                    indicatorColor = MaterialTheme.colorScheme.primary
                ),
                // Inside your loop
                icon = { Icon(imageVector = it.icon, contentDescription = null) },
                label = { Text(it.label) },

                selected = currentRoute == it.route,
                onClick = {
                    if (currentRoute != it.route) {
                        navController.navigate(it.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    }

                })
        }

    }
}