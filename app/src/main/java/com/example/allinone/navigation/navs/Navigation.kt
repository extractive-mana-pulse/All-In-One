package com.example.allinone.navigation.navs

import VisibilityOfUI
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.allinone.navigation.NavigationDrawer
import com.example.allinone.navigation.graph.Graph
import com.example.allinone.navigation.screen.PlCoding
import com.example.allinone.navigation.screen.SettingsScreens
import com.example.data.OnBoardingPreferences
import com.example.data.firebase.GoogleAuthUiClient
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.launch

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
    isDarkTheme: Boolean,
    isReadingMode: Boolean,
    onThemeChanged: (Boolean) -> Unit,
    fusedLocationClient: FusedLocationProviderClient,
    scheduleToggleState: Boolean
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Lazy-init auth client once
    val googleAuthUiClient = remember { GoogleAuthUiClient() }
    val onBoardingPrefs = remember { OnBoardingPreferences(context) }

    // UI state
    val topBarState = rememberSaveable { mutableStateOf(true) }
    val bottomBarState = rememberSaveable { mutableStateOf(true) }
    val gesturesEnabledState = rememberSaveable { mutableStateOf(true) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // Computed once
    val startDestination = remember {
        when {
            !onBoardingPrefs.isOnBoardingCompleted() -> Graph.ONBOARDING
            googleAuthUiClient.getSignedInUser() != null -> Graph.HOME
            else -> Graph.AUTH
        }
    }

    VisibilityOfUI(
        gesturesEnabledState = gesturesEnabledState,
        navBackStackEntry = navBackStackEntry,
        bottomBarState = bottomBarState,
        topBarState = topBarState
    )

    // Helper to toggle drawer
    fun toggleDrawer() = scope.launch {
        if (drawerState.isClosed) drawerState.open() else drawerState.close()
    }

    NavigationDrawer(
        navController = navController,
        bottomBarState = bottomBarState,
        gesturesEnabledState = gesturesEnabledState,
        drawerState = drawerState,
        onNavigateToPLCoding = {
            navController.navigate(PlCoding.Home)
            toggleDrawer()
        },
        onNavigateToSettings = {
            navController.navigate(SettingsScreens.Settings)
            toggleDrawer()
        },
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.padding(
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                )
            ) {
                onBoardingNavigation(navController)
                authNavigation(navController)
                mainNavigation(
                    context = context,
                    navController = navController,
                    drawerState = drawerState,
                    googleAuthUiClient = googleAuthUiClient,
                )
                settingsNavigation(
                    navController = navController,
                    isDarkTheme = isDarkTheme,
                    onThemeChanged = onThemeChanged,
                    fusedLocationClient = fusedLocationClient,
                    readingMode = isReadingMode,
                    scheduleToggleState = scheduleToggleState
                )
                plCodingNavigation(navController)
            }
        }
    )
}