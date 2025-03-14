package com.example.allinone.core.util.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavBackStackEntry
import com.example.allinone.navigation.Screens

@Composable
fun VisibilityOfUI(
    gesturesEnabledState: MutableState<Boolean>,
    navBackStackEntry: NavBackStackEntry?,
    bottomBarState: MutableState<Boolean>,
    topBarState: MutableState<Boolean>,
) {
    when (navBackStackEntry?.destination?.route) {
        Screens.Home.route -> {
            bottomBarState.value = true
            topBarState.value = true
            gesturesEnabledState.value = true
        }
        Screens.Profile.route -> {
            bottomBarState.value = true
            topBarState.value = false
            gesturesEnabledState.value = false
        }
        Screens.Settings.route -> {
            bottomBarState.value = false
            topBarState.value = false
            gesturesEnabledState.value = false
        }
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