package com.example.presentation.sealed

sealed class RegistrationFormEvent {

    data class PasswordChanged(val password: String) : RegistrationFormEvent()

    object ClearPasswordError : RegistrationFormEvent()

    object ForgotPassword : RegistrationFormEvent()

    object CreateAccount : RegistrationFormEvent()

    object SignInWithGoogle : RegistrationFormEvent()

    object Submit: RegistrationFormEvent()
}