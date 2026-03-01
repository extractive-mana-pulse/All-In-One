package com.example.allinone.navigation.navs

import VisibilityOfUI
import android.content.Context
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
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.allinone.navigation.NavigationDrawer
import com.example.allinone.navigation.graph.Graph
import com.example.allinone.navigation.screen.PlCoding
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
    context: Context,
    scheduleToggleState: Boolean
) {
    val googleAuthUiClient by lazy { GoogleAuthUiClient() }
    val scope = rememberCoroutineScope()
    val topBarState = rememberSaveable { mutableStateOf(true) }
    val bottomBarState = rememberSaveable { mutableStateOf(true) }
    val gesturesEnabledState = rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val onBoardingPrefs = remember { OnBoardingPreferences(context) }
    val isOnBoardingCompleted = remember { onBoardingPrefs.isOnBoardingCompleted() }
    val currentUser = remember { googleAuthUiClient.getSignedInUser() }

    VisibilityOfUI(
        gesturesEnabledState = gesturesEnabledState,
        navBackStackEntry = navBackStackEntry,
        bottomBarState = bottomBarState,
        topBarState = topBarState
    )

    NavigationDrawer(
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = when {
                    !isOnBoardingCompleted -> Graph.ONBOARDING
                    currentUser != null -> Graph.HOME
                    else -> Graph.AUTH
                },
                modifier = Modifier.padding(
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                )
            ) {
                onBoardingNavigation(navController = navController)
                authNavigation(navController = navController)
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
        },
        navController = navController,
        bottomBarState = bottomBarState,
        gesturesEnabledState = gesturesEnabledState,
        drawerState = drawerState,
        onNavigateToPLCoding = {
            navController.navigate(PlCoding.Home)
            scope.launch {
                if (drawerState.isClosed) {
                    drawerState.open()
                } else {
                    drawerState.close()
                }
            }
        }
    )
}