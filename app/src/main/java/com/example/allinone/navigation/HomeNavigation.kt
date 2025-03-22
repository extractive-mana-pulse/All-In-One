package com.example.allinone.navigation

import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.allinone.main.presentation.screens.DetailsScreen
import com.example.allinone.main.presentation.screens.HelpAndFeedbackScreen
import com.example.allinone.main.presentation.screens.HomeScreen

internal fun NavGraphBuilder.mainNavigation(
    navController: NavHostController,
    topBarState: MutableState<Boolean>,
    drawerState: DrawerState
) {
    navigation(
        startDestination = HomeScreens.Home.route,
        route = Graph.HOME
    ) {
        composable(
            HomeScreens.Home.route,
            enterTransition = { expandHorizontally() + fadeIn() },
            exitTransition = { shrinkHorizontally() + fadeOut() }
        ) {
            HomeScreen(
                navController = navController,
                topBarState = topBarState,
                drawerState = drawerState
            )
        }
        composable(HomeScreens.Help.route) {
            HelpAndFeedbackScreen(
                navController = navController
            )
        }
        composable<HomeScreens.DetailsScreen> {
            val argument = it.toRoute<HomeScreens.DetailsScreen>()
            DetailsScreen(
                navController = navController,
                id = argument.id
            )
        }
        codelabsNavigation(
            navController = navController
        )
        profileNavigation(
            navController = navController
        )
    }
}