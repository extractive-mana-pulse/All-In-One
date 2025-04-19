package com.example.allinone.auth.domain.model

data class UserCredentials(
    val id: String = "",
    val email: String = "",
    val displayName: String = "",
    val imageUrl: String,
    val password: String = ""
)