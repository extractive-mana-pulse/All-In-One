package com.example.allinone.navigation.navs

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.allinone.navigation.screen.CodeLabScreens
import com.example.presentation.ComposeArticleScreen
import com.example.presentation.Lemonade
import com.example.presentation.TaskManager
import com.example.presentation.art_space_app.ArtSpace
import com.example.presentation.business_card_app.BusinessCard
import com.example.presentation.quadrant_app.ComposeQuadrant
import com.example.presentation.tip_calculator_app.TipTimeLayout
import com.example.presentation.woof_app.WoofApp

fun NavGraphBuilder.codelabsNavigation(
    navController: NavHostController
) {
    composable<CodeLabScreens.TipCalculator>(
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
        TipTimeLayout(
            onNavigateUp = {
                navController.navigateUp()
            }
        )
    }
    composable<CodeLabScreens.Quadrant>(
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
    composable<CodeLabScreens.Lemonade>(
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
        Lemonade(
            onNavigateUp = {
                navController.navigateUp()
            }
        )
    }
    composable<CodeLabScreens.ComposeArticleScreen>(
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
        ComposeArticleScreen(
            onNavigateToTaskManager = {
                navController.navigate(CodeLabScreens.TaskManagerScreen)
            },
            onNavigateUp = {
                navController.navigateUp()
            }
        )
    }
    composable<CodeLabScreens.TaskManagerScreen> {
        TaskManager(
            onNavigateUp = {
                navController.navigateUp()
            }
        )
    }
    composable<CodeLabScreens.ArtSpace>(
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
        ArtSpace(
            onNavigateUp = {
                navController.navigateUp()
            }
        )
    }

    composable<CodeLabScreens.BusinessCard> {
        BusinessCard()
    }

    composable<CodeLabScreens.Woof> {
        WoofApp()
    }
}
