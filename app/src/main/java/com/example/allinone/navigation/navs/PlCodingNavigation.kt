package com.example.allinone.navigation.navs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.allinone.navigation.screen.Screens
import com.example.allinone.plcoding.PLCodingScreen
import com.example.allinone.plcoding.mini_challenges.presentation.MiniChallengesScreen
import com.example.allinone.plcoding.mini_challenges.presentation.december.WinterGreetingEditorScreen
import com.example.allinone.plcoding.mini_challenges.presentation.july.CollapsibleChatThreadRoot

internal fun NavGraphBuilder.plCodingNavigation(navController: NavHostController) {
    composable(Screens.PLCoding.route) {
        PLCodingScreen(
            onNavigateUpFromPlCodingScreen = {
                navController.navigateUp()
            },
            onNavigateToMiniChallenges = {
                navController.navigate(Screens.PLCoding.MiniChallenges.route)
            },
            onNavigateToAppChallenges = {
                navController.navigate(Screens.PLCoding.AppChallenges.route)
            }
        )
    }
    composable(Screens.PLCoding.MiniChallenges.route) {
        MiniChallengesScreen(
            onNavigateUpFromMiniChallengesScreen = { navController.navigateUp() },
            navigateToChallengeByString = { id -> navController.navigate(id) }
        )
    }
    composable(Screens.PLCoding.MiniChallenges.December.WinterGreetingEditor.route) {
        WinterGreetingEditorScreen()
    }
    composable(Screens.PLCoding.MiniChallenges.July.CollapsibleChatThread.route) {
        CollapsibleChatThreadRoot()
    }
}