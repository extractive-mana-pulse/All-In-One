package com.example.allinone.navigation.navs

import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.allinone.navigation.graph.Graph
import com.example.allinone.navigation.screen.ProfileScreens
import com.example.data.firebase.AuthenticationManager
import com.example.presentation.editProfile.EditProfileScreen
import com.example.presentation.profile.ProfileScreen

internal fun NavGraphBuilder.profileNavigation(
    navController: NavHostController,
    authenticationManager: AuthenticationManager
) {
    navigation(
        startDestination = ProfileScreens.Profile::class.qualifiedName ?: "",
        route = Graph.PROFILE
    ) {
        composable<ProfileScreens.Profile>(
            enterTransition = { expandHorizontally() + fadeIn() },
            exitTransition = { shrinkHorizontally() + fadeOut() }
        ) {
            ProfileScreen(
                userCredentials = authenticationManager.getSignedInUser(),
                onNavigateUp = {
                    navController.navigateUp()
                },
                onNavigateToEditProfile = {
                    navController.navigate(ProfileScreens.EditProfile)
                },
                onNavigateToSignOut = {
                    authenticationManager.signOut()
                    navController.navigate(Graph.AUTH)
                }
            )
        }
        composable<ProfileScreens.EditProfile>(
            enterTransition = { expandHorizontally() + fadeIn() },
            exitTransition = { shrinkHorizontally() + fadeOut() }
        ) {
            EditProfileScreen(
                navController = navController
            )
        }
    }
}