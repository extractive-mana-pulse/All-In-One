package com.example.allinone.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.MutableState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.allinone.screens.ArtSpace
import com.example.allinone.screens.AutoNightModeScreen
import com.example.allinone.screens.ComposeArticleScreen
import com.example.allinone.screens.ComposeQuadrant
import com.example.allinone.screens.HomeScreen
import com.example.allinone.screens.Lemonade
import com.example.allinone.screens.ProfileScreen
import com.example.allinone.screens.Screens
import com.example.allinone.screens.TaskManager
import com.example.allinone.screens.TipTimeLayout
import com.example.allinone.settings.SettingScreen

internal fun NavGraphBuilder.appNavigationTest(
    navController: NavHostController,
    topBarState: MutableState<Boolean>,
    drawerState: DrawerState
) {
    navigation(
        startDestination = Screens.Home.route,
        route = Graph.HOME
    ) {
        composable(
            Screens.Home.route,
            enterTransition = { expandHorizontally() + fadeIn() },
            exitTransition = { shrinkHorizontally() + fadeOut() }
        ) {
            HomeScreen(
                navController = navController,
                topBarState = topBarState,
                drawerState = drawerState
            )
        }
        composable(
            Screens.Profile.route,
            enterTransition = { expandHorizontally() + fadeIn() },
            exitTransition = { shrinkHorizontally() + fadeOut() }
        ) {
            ProfileScreen(navController = navController)
        }
        composable(
            Screens.Settings.route,
            enterTransition = { expandHorizontally() + fadeIn() },
            exitTransition = { shrinkHorizontally() + fadeOut() }
        ) {
            SettingScreen(navController = navController)
        }
        composable(Screens.Night.route) {
            AutoNightModeScreen(navController = navController)
        }
        composable(
            Screens.TipCalculator.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { -80 },
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { -80 },
                    animationSpec = tween(
                        durationMillis = 1200,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 1200,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        ) {
            TipTimeLayout(navController = navController)
        }
        composable(
            Screens.Quadrant.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { -80 },
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { -80 },
                    animationSpec = tween(
                        durationMillis = 1200,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 1200,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        ) {
            ComposeQuadrant(navController = navController)
        }
        composable(
            Screens.Lemonade.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { -80 },
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { -80 },
                    animationSpec = tween(
                        durationMillis = 1200,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 1200,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        ) {
            Lemonade(navController = navController)
        }
        composable(
            Screens.ComposeArticleScreen.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { -80 },
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { -80 },
                    animationSpec = tween(
                        durationMillis = 1200,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 1200,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        ) {
            ComposeArticleScreen(navController = navController)
        }
        composable(Screens.TaskManagerScreen.route) {
            TaskManager(navController = navController)
        }
        composable(Screens.ArtSpace.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { -80 },
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { -80 },
                    animationSpec = tween(
                        durationMillis = 1200,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 1200,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        ) {
            ArtSpace(navController = navController)
        }
    }
}