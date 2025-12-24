package com.example.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavBackStackEntry
import com.example.allinone.navigation.screen.HomeScreens
import com.example.allinone.navigation.screen.ProfileScreens
import com.example.allinone.navigation.screen.Screens
import com.example.allinone.navigation.screen.SettingsScreens

@Composable
fun VisibilityOfUI(
    gesturesEnabledState: MutableState<Boolean>,
    navBackStackEntry: NavBackStackEntry?,
    bottomBarState: MutableState<Boolean>,
    topBarState: MutableState<Boolean>,
) {
    when (navBackStackEntry?.destination?.route) {
        HomeScreens.Home.route -> {
            bottomBarState.value = false
            topBarState.value = true
            gesturesEnabledState.value = true
        }
        ProfileScreens.Profile.route,
        SettingsScreens.Settings.route,
        Screens.ComposeArticleScreen.route,
        Screens.TipCalculator.route,
        Screens.Quadrant.route,
        Screens.ArtSpace.route,
        Screens.Lemonade.route,
        Screens.TaskManagerScreen.route -> {
            bottomBarState.value = false
            topBarState.value = false
            gesturesEnabledState.value = false
        }
        else -> {
            bottomBarState.value = false
            topBarState.value = false
            gesturesEnabledState.value = false
        }
    }
}