package com.example.domain.model

sealed interface AuthResult {

    data object Success: AuthResult
    data class Error(val message: String) : AuthResult
    data class Loading(val progress: Int) : AuthResult

}