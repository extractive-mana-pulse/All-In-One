package com.example.domain.model

sealed interface LocationResult {
    data object Loading : LocationResult
    data class Success(val address: String) : LocationResult
    data class Error(val message: String) : LocationResult
}