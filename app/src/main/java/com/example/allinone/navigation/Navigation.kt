package com.example.allinone.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.allinone.screens.ArtSpace
import com.example.allinone.screens.ComposeArticleScreen
import com.example.allinone.screens.ComposeQuadrant
import com.example.allinone.screens.HomeScreen
import com.example.allinone.screens.Lemonade
import com.example.allinone.screens.ProfileScreen
import com.example.allinone.screens.Screens
import com.example.allinone.screens.TaskManager
import com.example.allinone.screens.TipTimeLayout
import com.example.allinone.settings.SettingScreen
import com.example.allinone.util.ui.BottomNavigationBar

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController()
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val topBarState = rememberSaveable { (mutableStateOf(true)) }

    when (navBackStackEntry?.destination?.route) {
        Screens.Home.route -> {
            bottomBarState.value = true
        }
        Screens.Profile.route -> {
            bottomBarState.value = true
        }
        Screens.Settings.route -> {
            bottomBarState.value = true
        }
        Screens.ComposeArticleScreen.route -> {
            bottomBarState.value = false
        }
        Screens.TipCalculator.route -> {
            bottomBarState.value = false
        }
        Screens.Quadrant.route -> {
            bottomBarState.value = false
        }
        Screens.ArtSpace.route -> {
            bottomBarState.value = false
        }
        Screens.TaskManagerScreen.route -> {
            bottomBarState.value = false
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                bottomBarState = bottomBarState,
                navController = navController
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(
                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                // Only apply top padding if top bar is visible
                top = if (topBarState.value) innerPadding.calculateTopPadding() else 0.dp,
                end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                // Only apply bottom padding if bottom bar is visible
                bottom = if (bottomBarState.value) innerPadding.calculateBottomPadding() else 0.dp
            )
        ) {
            composable(
                Screens.Home.route,
                enterTransition = { expandHorizontally() + fadeIn() },
                exitTransition = { shrinkHorizontally() + fadeOut() }
                ) {
                HomeScreen(navController = navController)
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
}