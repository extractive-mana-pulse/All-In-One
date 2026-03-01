package com.example.allinone.navigation.navs

import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.allinone.navigation.graph.Graph
import com.example.allinone.navigation.screen.ProfileScreens
import com.example.data.firebase.GoogleAuthUiClient
import com.example.presentation.editProfile.EditProfileScreen
import com.example.presentation.profile.ProfileScreen
import com.example.presentation.helper.toastMessage
import kotlinx.coroutines.launch

internal fun NavGraphBuilder.profileNavigation(
    navController: NavHostController,
    googleAuthUiClient: GoogleAuthUiClient
) {
    navigation(
        startDestination = ProfileScreens.Profile::class.qualifiedName ?: "",
        route = Graph.PROFILE
    ) {
        composable<ProfileScreens.Profile>(
            enterTransition = { expandHorizontally() + fadeIn() },
            exitTransition = { shrinkHorizontally() + fadeOut() }
        ) {
            val scope = rememberCoroutineScope()
            val context = LocalContext.current
            ProfileScreen(
                userData = googleAuthUiClient.getSignedInUser(),
                onNavigateUp = {
                    navController.navigateUp()
                },
                onNavigateToEditProfile = {
                    navController.navigate(ProfileScreens.EditProfile)
                },
                onNavigateToSignOut = {
                    scope.launch {
                        googleAuthUiClient.signOut()
                        navController.navigate(Graph.AUTH) {
                            popUpTo(Graph.HOME) {
                                inclusive = true
                            }
                        }
                    }
                },
                onShareProfileClick = {
                    toastMessage(
                        context = context,
                        message = "In maintenance"
                    )
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