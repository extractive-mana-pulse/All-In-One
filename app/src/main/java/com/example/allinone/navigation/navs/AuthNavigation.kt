package com.example.allinone.navigation.navs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.allinone.navigation.graph.Graph
import com.example.allinone.navigation.screen.AuthScreens
import com.example.presentation.forgot_password.ForgotPasswordScreen
import com.example.presentation.sign_in.SignInScreenRoot
import com.example.presentation.sign_up.SignUpScreenRoot

internal fun NavGraphBuilder.authNavigation(
    navController: NavHostController
) {
    navigation(
        startDestination = AuthScreens.SignIn::class.qualifiedName ?: "",
        route = Graph.AUTH
    ) {
        composable<AuthScreens.SignIn> {
            SignInScreenRoot(
                onNavigateToSignUp = {
                    navController.navigate(AuthScreens.SignUp)
                },
                onNavigateToForgotPassword = {
                    navController.navigate(AuthScreens.ForgotPassword)
                },
                onNavigateToHome = {
                    navController.navigate(Graph.HOME)
                },
            )
        }
        composable<AuthScreens.SignUp> {
            SignUpScreenRoot(
                onNavigateToSignIn = {
                    navController.navigate(AuthScreens.SignIn)
                },
            )
        }
        composable<AuthScreens.ForgotPassword>{
            ForgotPasswordScreen()
        }
    }
}
