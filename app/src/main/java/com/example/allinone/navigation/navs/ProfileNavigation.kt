package com.example.allinone.navigation.navs

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.allinone.navigation.graph.Graph
import com.example.allinone.navigation.screen.ProfileScreens
import com.example.data.firebase.GoogleAuthUiClient
import com.example.presentation.editProfile.EditProfileScreen
import com.example.presentation.profile.ProfileScreen
import kotlinx.coroutines.launch

internal fun NavGraphBuilder.profileNavigation(
    context: Context,
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
                        Toast.makeText(
                            context,
                            "Signed out",
                            Toast.LENGTH_LONG
                        ).show()

                        navController.popBackStack()
                    }
                },
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