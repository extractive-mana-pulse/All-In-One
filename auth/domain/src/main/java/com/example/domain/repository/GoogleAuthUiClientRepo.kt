package com.example.domain.repository

import com.example.domain.model.SignInResult
import com.example.domain.model.UserData

interface GoogleAuthUiClientRepo {

    suspend fun signInWithCredential(idToken: String): SignInResult

    suspend fun signOut()

    fun getSignedInUser(): UserData?

}