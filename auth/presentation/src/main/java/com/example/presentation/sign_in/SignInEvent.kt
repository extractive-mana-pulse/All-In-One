package com.example.presentation.sign_in

sealed class SignInEvent {
    object Success: SignInEvent()
    object NavigateToHome: SignInEvent()
    data class Error(val message: String): SignInEvent()
}