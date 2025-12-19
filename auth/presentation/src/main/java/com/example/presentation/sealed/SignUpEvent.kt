package com.example.presentation.sealed

sealed class SignUpEvent {

    data class PasswordChanged(val password: String) : SignUpEvent()
    object ClearPasswordError : SignUpEvent()
    object Submit: SignUpEvent()

}