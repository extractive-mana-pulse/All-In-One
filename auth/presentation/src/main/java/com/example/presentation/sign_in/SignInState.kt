package com.example.presentation.sign_in

import com.example.domain.model.UserData

data class SignInState(
    val email: String = "",
    val emailError: String? = null,

    val password: String = "",
    val passwordError: String? = null,

    val isLoading: Boolean = false,
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,

    val userData: UserData? = null
)
