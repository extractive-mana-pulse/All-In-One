package com.example.allinone.profile.domain.model

import android.net.Uri

data class EditProfileUiState(
    val displayName: String = "",
    val username: String = "",
    val profilePictureUrl: Uri? = null,
    val selectedImageUri: Uri? = null,
    val email: String = ""
)