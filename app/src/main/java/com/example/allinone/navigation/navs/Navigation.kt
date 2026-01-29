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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.allinone.navigation.NavigationDrawer
import com.example.allinone.navigation.graph.Graph
import com.example.allinone.navigation.screen.PlCoding
import com.example.data.firebase.GoogleAuthUiClient
import com.example.presentation.sign_in.SignInViewModel
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
    val signInViewModel: SignInViewModel = hiltViewModel()

    val state = signInViewModel.state.collectAsStateWithLifecycle()

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
                startDestination = if (state.value.isSignInSuccessful) Graph.HOME else Graph.AUTH,
                modifier = Modifier.padding(
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
//                    top = if (topBarState.value) innerPadding.calculateTopPadding() else 0.dp,
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
//                    bottom = if (bottomBarState.value) innerPadding.calculateBottomPadding() else 0.dp
                )
            ) {
                authNavigation(
                    navController = navController,
                    applicationContext = context,
                    googleAuthUiClient = googleAuthUiClient
                )
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
                // later implement auth & onBoarding graphs
                plCodingNavigation(navController)
            }
        },
        navController = navController,
        bottomBarState = bottomBarState,
        gesturesEnabledState = gesturesEnabledState,
        drawerState = drawerState,
        onNavigateToPLCoding = {
            navController.navigate(PlCoding)
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