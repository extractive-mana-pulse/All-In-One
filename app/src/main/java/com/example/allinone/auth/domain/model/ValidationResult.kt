package com.example.allinone.auth.domain.model

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)