package com.radiusagent.assignment.presentation.base.navigation

sealed class Screen(override val route: String) : NavigationDestination {
    object Home : Screen("home_screen")
}