package com.example.allinone.navigation.navs

import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.allinone.auth.data.remote.impl.AuthenticationManager
import com.example.allinone.navigation.graph.Graph
import com.example.allinone.navigation.screen.ProfileScreens
import com.example.allinone.profile.presentation.screens.EditProfileScreen
import com.example.allinone.profile.presentation.screens.ProfileScreen

internal fun NavGraphBuilder.profileNavigation(
    navController: NavHostController,
    authenticationManager: AuthenticationManager
) {
    navigation(
        startDestination = ProfileScreens.Profile.route,
        route = Graph.PROFILE
    ) {
        composable(
            ProfileScreens.Profile.route,
            enterTransition = { expandHorizontally() + fadeIn() },
            exitTransition = { shrinkHorizontally() + fadeOut() }
        ) {
            ProfileScreen(
                navController = navController,
                userCredentials = authenticationManager.getSignedInUser()
            )
        }
        composable(
            ProfileScreens.EditProfile.route,
            enterTransition = { expandHorizontally() + fadeIn() },
            exitTransition = { shrinkHorizontally() + fadeOut() }
        ) {
            EditProfileScreen(
                navController = navController
            )
        }
    }
}