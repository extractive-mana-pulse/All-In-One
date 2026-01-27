package com.example.domain

data class UserCredentials(
    val id: String = "",
    val email: String = "",
    val displayName: String = "",
    val imageUrl: String? = null,
    val password: String = ""
)