package com.example.allinone.navigation.navs

import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.allinone.navigation.graph.Graph
import com.example.allinone.navigation.screen.SettingsScreens
import com.example.allinone.settings.autoNight.presentation.screens.AdaptiveModeScreen
import com.example.allinone.settings.autoNight.presentation.screens.AutoNightModeScreen
import com.example.allinone.settings.autoNight.presentation.screens.ScheduledModeScreen
import com.example.allinone.settings.autoNight.presentation.screens.SettingScreen
import com.example.allinone.settings.batterySafe.presentation.screens.BatterySavingScreen
import com.example.allinone.settings.deviceTemp.presentation.screens.AllSensorsScreen
import com.example.allinone.settings.deviceTemp.presentation.screens.DeviceTempScreen
import com.google.android.gms.location.FusedLocationProviderClient

internal fun NavGraphBuilder.settingsNavigation(
    navController: NavHostController,
    isDarkTheme: Boolean,
    readingMode: Boolean,
    onThemeChanged: (Boolean) -> Unit,
    fusedLocationClient: FusedLocationProviderClient
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
            SettingScreen(
                navController = navController,
                isReadingMode = readingMode
            )
        }
        composable(SettingsScreens.Night.route) {
            AutoNightModeScreen(
                navController = navController,
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
        composable(SettingsScreens.ScheduledMode.route) {
            ScheduledModeScreen(
                navController = navController,
                isDarkTheme = isDarkTheme,
                onThemeChanged = onThemeChanged,
                fusedLocationClient = fusedLocationClient
            )
        }
        composable(SettingsScreens.Temperature.route) {
            DeviceTempScreen(
                navController = navController
            )
        }
        composable(SettingsScreens.AllSensors.route) {
            AllSensorsScreen(
                navController = navController
            )
        }
    }
}