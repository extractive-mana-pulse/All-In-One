package com.example.allinone.auth.presentation.sealed

sealed class RegistrationFormEvent {

    data class EmailChanged(val email: String) : RegistrationFormEvent()

    data class PasswordChanged(val password: String) : RegistrationFormEvent()

    object ForgotPassword : RegistrationFormEvent()

    object CreateAccount : RegistrationFormEvent()

    object SignInWithGoogle : RegistrationFormEvent()

    object Submit: RegistrationFormEvent()
}