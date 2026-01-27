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
import com.example.allinone.navigation.screen.HomeScreens
import com.example.allinone.navigation.screen.SettingsScreens
import com.example.presentation.autoNight.screens.AdaptiveModeScreen
import com.example.presentation.autoNight.screens.AutoNightModeScreen
import com.example.presentation.autoNight.screens.ScheduledModeScreen
import com.example.presentation.autoNight.screens.SettingScreen
import com.example.presentation.batterySafe.screens.BatterySavingScreen
import com.example.presentation.deviceTemp.screens.AllSensorsScreen
import com.example.presentation.deviceTemp.screens.DeviceTempScreen
import com.google.android.gms.location.FusedLocationProviderClient

internal fun NavGraphBuilder.settingsNavigation(
    navController: NavHostController,
    isDarkTheme: Boolean,
    readingMode: Boolean,
    onThemeChanged: (Boolean) -> Unit,
    fusedLocationClient: FusedLocationProviderClient,
    scheduleToggleState: Boolean
) {
    navigation(
        startDestination = SettingsScreens.Settings::class.qualifiedName ?: "",
        route = Graph.SETTINGS
    ) {
        composable<SettingsScreens.Settings>(
            enterTransition = { expandHorizontally() + fadeIn() },
            exitTransition = { shrinkHorizontally() + fadeOut() }
        ) {
            SettingScreen(
                isReadingMode = readingMode,
                onNavigateBack = {
                    navController.navigateUp()
                },
                onNavigateToNight = {
                    navController.navigate(SettingsScreens.Night)
                },
                onNavigateToPowerSavingMode = {
                    navController.navigate(SettingsScreens.PowerSaving)
                },
                onNavigateToTemperature = {
                    navController.navigate(SettingsScreens.Temperature)
                }
            )
        }
        composable<SettingsScreens.Night> {
            AutoNightModeScreen(
                onThemeChanged = onThemeChanged,
                onNavigateToScheduleMode = {
                    navController.navigate(SettingsScreens.ScheduledMode)
                },
                onNavigateToAdaptiveMode = {
                    navController.navigate(SettingsScreens.AdaptiveMode)
                }
            )
        }
        composable<SettingsScreens.PowerSaving> {
            BatterySavingScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<SettingsScreens.AdaptiveMode> {
            AdaptiveModeScreen(
                navController = navController,
                isDarkTheme = isDarkTheme,
                onThemeChanged = onThemeChanged
            )
        }
        composable<SettingsScreens.ScheduledMode> {
            ScheduledModeScreen(
                navController = navController,
                isDarkTheme = isDarkTheme,
                onThemeChanged = onThemeChanged,
                fusedLocationClient = fusedLocationClient,
                scheduleToggleState = scheduleToggleState
            )
        }
        composable<SettingsScreens.Temperature> {
            DeviceTempScreen(
                onNavigateBack = {
                    navController.navigateUp()
                },
                onNavigateToHelp = {
                    navController.navigate(HomeScreens.Help)
                },
                onNavigateToAllSensors = {
                    navController.navigate(SettingsScreens.AllSensors)
                }
            )
        }
        composable<SettingsScreens.AllSensors> {
            AllSensorsScreen(
                navController = navController
            )
        }
    }
}