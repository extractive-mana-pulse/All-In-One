package com.example.allinone.navigation.navs

import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.allinone.navigation.graph.Graph
import com.example.data.OnBoardingPreferences
import com.example.presentation.OnBoardingScreen

fun NavGraphBuilder.onBoardingNavigation(
    navController: NavHostController
) {
    navigation(
        route = Graph.ONBOARDING,
        startDestination = OnBoardingRoute.OnBoarding.route
    ) {
        composable(route = OnBoardingRoute.OnBoarding.route) {
            val context = LocalContext.current
            val onBoardingPrefs = remember { OnBoardingPreferences(context) }

            OnBoardingScreen(
                onFinish = {
                    // Save that onboarding is completed
                    onBoardingPrefs.setOnBoardingCompleted(true)

                    // Navigate to auth screen
                    navController.navigate(Graph.AUTH) {
                        popUpTo(Graph.ONBOARDING) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

sealed class OnBoardingRoute(val route: String) {
    data object OnBoarding : OnBoardingRoute("onboarding_screen")
}