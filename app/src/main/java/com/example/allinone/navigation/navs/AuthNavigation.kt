package com.example.allinone.navigation.navs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.allinone.auth.presentation.screens.ForgotPasswordScreen
import com.example.allinone.auth.presentation.screens.SignInScreen
import com.example.allinone.auth.presentation.screens.SignUpScreen
import com.example.allinone.navigation.graph.Graph
import com.example.allinone.navigation.screen.AuthScreens

internal fun NavGraphBuilder.authNavigation(
    navController: NavHostController
) {
    navigation(
        startDestination = AuthScreens.SignIn.route,
        route = Graph.AUTH
    ) {
        composable(
            AuthScreens.SignIn.route
        ) {
            SignInScreen(
                navController = navController
            )
        }
        composable(
            AuthScreens.SignUp.route
        ) {
            SignUpScreen(
                navController = navController
            )
        }
        composable(
            AuthScreens.ForgotPassword.route
        ) {
            ForgotPasswordScreen(
                navController = navController
            )
        }
    }
}
