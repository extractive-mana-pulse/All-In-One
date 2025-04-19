package com.example.allinone.auth.presentation.sealed

sealed class ValidationEvent {
    object Success: ValidationEvent()
    object NavigateToHome: ValidationEvent()
    data class Error(val message: String): ValidationEvent()
}