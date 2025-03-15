package com.example.allinone.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.allinone.codelabs.presentation.screens.ArtSpace
import com.example.allinone.codelabs.presentation.screens.ComposeArticleScreen
import com.example.allinone.codelabs.presentation.screens.ComposeQuadrant
import com.example.allinone.codelabs.presentation.screens.Lemonade
import com.example.allinone.codelabs.presentation.screens.TaskManager
import com.example.allinone.codelabs.presentation.screens.TipTimeLayout

fun NavGraphBuilder.codelabsNavigation(
    navController: NavHostController
) {
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
        ComposeQuadrant()
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
