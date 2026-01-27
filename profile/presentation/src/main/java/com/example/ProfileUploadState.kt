package com.example

import android.net.Uri

sealed class ProfileUploadState {

    object Idle : ProfileUploadState()
    data class Loading(val progress: Int) : ProfileUploadState()
    data class Error(val message: String) : ProfileUploadState()
    data class Success(val photoUrl: Uri) : ProfileUploadState()

}