package com.example.allinone.navigation

import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.allinone.profile.presentation.screens.EditProfileScreen
import com.example.allinone.profile.presentation.screens.ProfileScreen

internal fun NavGraphBuilder.profileNavigation(
    navController: NavHostController
) {
    navigation(
        startDestination = Screens.Profile.route,
        route = Graph.PROFILE
    ) {
        composable(
            Screens.Profile.route,
            enterTransition = { expandHorizontally() + fadeIn() },
            exitTransition = { shrinkHorizontally() + fadeOut() }
        ) {
            ProfileScreen(navController = navController)
        }
        composable(
            Screens.EditProfile.route,
            enterTransition = { expandHorizontally() + fadeIn() },
            exitTransition = { shrinkHorizontally() + fadeOut() }
        ) {
            EditProfileScreen(navController = navController)
        }
    }
}