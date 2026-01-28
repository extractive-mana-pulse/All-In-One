package com.example.presentation.sign_up

sealed interface ValidationAction {
    object Success : ValidationAction
    object NavigateToHome : ValidationAction
    object LaunchGoogleSignIn : ValidationAction

    data class ShowError(val message: String?) : ValidationAction
}
