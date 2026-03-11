package com.example.allinone.navigation.navs

import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.allinone.navigation.graph.Graph
import com.example.allinone.navigation.screens.HomeScreens
import com.example.allinone.navigation.screens.ProfileScreens
import com.example.data.firebase.GoogleAuthUiClient
import com.example.presentation.details.DetailsRoot
import com.example.presentation.details.LeetcodeDetailScreen
import com.example.presentation.help.HelpAndFeedbackScreen
import com.example.presentation.home.HomeScreenRoot
import com.example.presentation.home.HomeViewModel
import com.example.presentation.home.SectionScreen

internal fun NavGraphBuilder.mainNavigation(
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
            HomeScreenRoot(
                onNavigateToProfile = {
                    navController.navigate(ProfileScreens.Profile)
                },
                onNavigateToDetailWithId = {
                    navController.navigate(HomeScreens.DetailsScreen(it))
                },
                drawerState = drawerState,
                userData = googleAuthUiClient.getSignedInUser(),
                onLeetCodeDetailWithId = {
                    navController.navigate(HomeScreens.LeetCodeDetailsScreen(it))
                },
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
        composable<HomeScreens.LeetCodeDetailsScreen> {
            val argument = it.toRoute<HomeScreens.LeetCodeDetailsScreen>()
            val homeViewModel: HomeViewModel = hiltViewModel()
            val algorithm by homeViewModel.getAlgorithmById(argument.id.toString())
                .collectAsStateWithLifecycle(initialValue = null)

            algorithm?.let { leetcode ->
                LeetcodeDetailScreen(
                    algorithm = leetcode,
                    onNavigateBack = { navController.navigateUp() }
                )
            }
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
            navController = navController,
            googleAuthUiClient = googleAuthUiClient
        )
    }
}