package com.example.allinone.navigation.navs

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.allinone.navigation.graph.Graph
import com.example.allinone.navigation.screen.AuthScreens
import com.example.data.firebase.GoogleAuthUiClient
import com.example.presentation.forgot_password.ForgotPasswordScreen
import com.example.presentation.sign_in.SignInScreenRoot
import com.example.presentation.sign_in.SignInViewModel
import com.example.presentation.sign_up.SignUpScreenRoot

internal fun NavGraphBuilder.authNavigation(
    navController: NavHostController,
    applicationContext: android.content.Context,
    googleAuthUiClient: GoogleAuthUiClient
) {
    navigation(
        startDestination = AuthScreens.SignIn::class.qualifiedName ?: "",
        route = Graph.AUTH
    ) {
        composable<AuthScreens.SignIn> {
            val context = LocalContext.current
            val viewModel = hiltViewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(Unit) {
                val userData = googleAuthUiClient.getSignedInUser()
                Log.d("UI", "UserData: $userData")
                Log.d("UI", "Profile Picture URL: ${userData?.profilePictureUrl}")
            }

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if(state.isSignInSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Sign in successful",
                        Toast.LENGTH_LONG
                    ).show()

                    navController.navigate(Graph.HOME)
                    viewModel.resetState()
                }
            }

            SignInScreenRoot(
                state = state,
                onSignInWithGoogle = {
                    viewModel.signIn(context)
                },
                onNavigateToSignUp = {
                    navController.navigate(AuthScreens.SignUp)
                },
                onNavigateToForgotPassword = {
                    navController.navigate(AuthScreens.ForgotPassword)
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