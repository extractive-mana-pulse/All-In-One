package com.example.allinone.navigation.navs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.allinone.navigation.screen.PlCoding
import com.example.presentation.MiniChallengesScreen
import com.example.presentation.PLCodingScreen
import com.example.presentation.december.WinterGreetingEditorScreen
import com.example.presentation.july.CollapsibleChatThreadRoot

internal fun NavGraphBuilder.plCodingNavigation(navController: NavHostController) {
    composable<PlCoding.Home> {
        PLCodingScreen(
            onNavigateUpFromPlCodingScreen = {
                navController.navigateUp()
            },
            onNavigateToMiniChallenges = {
                navController.navigate(PlCoding.Home.MiniChallenges)
            },
            onNavigateToAppChallenges = {
                navController.navigate(PlCoding.Home.AppChallenges)
            }
        )
    }
    composable<PlCoding.Home.MiniChallenges> {
        MiniChallengesScreen(
            onNavigateUpFromMiniChallengesScreen = { navController.navigateUp() },
            navigateToChallengeByString = { id -> navController.navigate(id) }
        )
    }
    composable<PlCoding.Home.MiniChallenges.December.WinterGreetingEditor> {
        WinterGreetingEditorScreen()
    }
    composable<PlCoding.Home.MiniChallenges.July.CollapsibleChatThread> {
        CollapsibleChatThreadRoot()
    }
}