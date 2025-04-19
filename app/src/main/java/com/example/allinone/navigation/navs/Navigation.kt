package com.example.allinone.navigation.navs

import android.content.Context
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.allinone.auth.data.remote.impl.AuthenticationManager
import com.example.allinone.core.util.ui.NavigationDrawer
import com.example.allinone.core.util.ui.VisibilityOfUI
import com.example.allinone.main.presentation.vm.TimerViewModel
import com.example.allinone.navigation.graph.Graph
import com.google.android.gms.location.FusedLocationProviderClient

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
    isDarkTheme: Boolean,
    isReadingMode: Boolean,
    timerViewModel: TimerViewModel,
    onThemeChanged: (Boolean) -> Unit,
    fusedLocationClient: FusedLocationProviderClient,
    context: Context,
    authenticationManager: AuthenticationManager
) {
    val topBarState = rememberSaveable { mutableStateOf(true) }
    val bottomBarState = rememberSaveable { mutableStateOf(true) }
    val gesturesEnabledState = rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

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
                startDestination = if (authenticationManager.isUserSignedIn()) Graph.HOME else Graph.AUTH,
                modifier = Modifier.padding(
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
//                    top = if (topBarState.value) innerPadding.calculateTopPadding() else 0.dp,
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
//                    bottom = if (bottomBarState.value) innerPadding.calculateBottomPadding() else 0.dp
                )
            ) {
                authNavigation(
                    navController = navController
                )
                mainNavigation(
                    navController = navController,
                    topBarState = topBarState,
                    drawerState = drawerState,
                    timerViewModel = timerViewModel,
                    context = context
                )
                settingsNavigation(
                    navController = navController,
                    isDarkTheme = isDarkTheme,
                    onThemeChanged = onThemeChanged,
                    fusedLocationClient = fusedLocationClient,
                    readingMode = isReadingMode,
                )
                // later implement auth & onBoarding graphs
            }
        },
        navController = navController,
        bottomBarState = bottomBarState,
        gesturesEnabledState = gesturesEnabledState,
        drawerState = drawerState
    )
}