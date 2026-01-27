package com.example.domain

import android.net.Uri

data class UserCredentials(
    val id: String = "",
    val email: String = "",
    val displayName: String = "",
    val imageUrl: Uri?,
    val password: String = ""
)