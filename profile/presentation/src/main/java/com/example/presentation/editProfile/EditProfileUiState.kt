package com.example.presentation.editProfile

import android.net.Uri

data class EditProfileUiState(
    val displayName: String = "",
    val username: String = "",
    val profilePictureUrl: String? = null,
    val selectedImageUri: Uri? = null,
    val email: String = ""
)