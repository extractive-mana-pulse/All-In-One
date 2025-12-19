package com.example.presentation.sealed

sealed interface AuthResponse {

    data object Success: AuthResponse
    data class Error(val message: String) : AuthResponse
    data class Loading(val progress: Int) : AuthResponse

}