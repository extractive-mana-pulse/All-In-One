package com.example.allinone.navigation

import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.allinone.settings.presentation.screens.AdaptiveModeScreen
import com.example.allinone.settings.presentation.screens.AutoNightModeScreen
import com.example.allinone.settings.presentation.screens.BatterySavingScreen
import com.example.allinone.settings.presentation.screens.SettingScreen

internal fun NavGraphBuilder.settingsNavigation(
    navController: NavHostController,
    isDarkTheme: Boolean,
    onThemeChanged: (Boolean) -> Unit
) {
    navigation(
        startDestination = SettingsScreens.Settings.route,
        route = Graph.SETTINGS
    ) {
        composable(
            SettingsScreens.Settings.route,
            enterTransition = { expandHorizontally() + fadeIn() },
            exitTransition = { shrinkHorizontally() + fadeOut() }
        ) {
            SettingScreen(navController = navController)
        }
        composable(SettingsScreens.Night.route) {
            AutoNightModeScreen(
                navController = navController,
                isDarkTheme = isDarkTheme,
                onThemeChanged = onThemeChanged
            )
        }
        composable(SettingsScreens.PowerSaving.route) {
            BatterySavingScreen(navController = navController)
        }
        composable(SettingsScreens.AdaptiveMode.route) {
            AdaptiveModeScreen(
                navController = navController,
                isDarkTheme = isDarkTheme,
                onThemeChanged = onThemeChanged
            )
        }
    }
}