package com.example.allinone.navigation.navs

import android.content.Context
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.material3.DrawerState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.allinone.navigation.graph.Graph
import com.example.allinone.navigation.screen.HomeScreens
import com.example.allinone.navigation.screen.ProfileScreens
import com.example.data.firebase.GoogleAuthUiClient
import com.example.presentation.details.DetailsRoot
import com.example.presentation.screens.HelpAndFeedbackScreen
import com.example.presentation.screens.HomeScreen
import com.example.presentation.screens.SectionScreen

internal fun NavGraphBuilder.mainNavigation(
    context: Context,
    navController: NavHostController,
    drawerState: DrawerState,
    googleAuthUiClient: GoogleAuthUiClient
) {
    navigation(
        startDestination = HomeScreens.Home::class.qualifiedName ?: "",
        route = Graph.HOME
    ) {
        composable<HomeScreens.Home>(
            enterTransition = { expandHorizontally() + fadeIn() },
            exitTransition = { shrinkHorizontally() + fadeOut() }
        ) {
            HomeScreen(
                onNavigateToProfile = {
                    navController.navigate(ProfileScreens.Profile)
                },
                onNavigateToSectionById = {
                    navController.navigate(HomeScreens.SectionScreen(it))
                },
                onNavigateToDetailWithId = {
                    navController.navigate(HomeScreens.DetailsScreen(it))
                },
                drawerState = drawerState,
                userData = googleAuthUiClient.getSignedInUser()
            )
        }
        composable<HomeScreens.Help> {
            HelpAndFeedbackScreen(
                navController = navController
            )
        }
        composable<HomeScreens.DetailsScreen> {
            val argument = it.toRoute<HomeScreens.DetailsScreen>()
            DetailsRoot(
                id = argument.id,
                navigateToCodeLabWithRoute = { route ->
                    navController.navigate(route)
                },
                onNavigateUp = {
                    navController.navigateUp()
                }
            )
        }
        composable<HomeScreens.SectionScreen> {
            val argument = it.toRoute<HomeScreens.SectionScreen>()
            SectionScreen(
                navController = navController,
                id = argument.id
            )
        }
        codelabsNavigation(
            navController = navController
        )
        profileNavigation(
            context = context,
            navController = navController,
            googleAuthUiClient = googleAuthUiClient
        )
    }
}