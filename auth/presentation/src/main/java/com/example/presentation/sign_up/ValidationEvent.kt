package com.example.presentation.sign_up

sealed class ValidationEvent {
    object Success: ValidationEvent()
    object NavigateToHome: ValidationEvent()
    data class Error(val message: String): ValidationEvent()
}