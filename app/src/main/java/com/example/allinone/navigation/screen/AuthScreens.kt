package com.example.allinone.navigation.screen

import kotlinx.serialization.Serializable

@Serializable
sealed interface AuthScreens {

    @Serializable
    object SignIn : AuthScreens

    @Serializable
    object SignUp : AuthScreens

    @Serializable
    object ForgotPassword : AuthScreens

    @Serializable
    object ResetPassword : AuthScreens

    @Serializable
    object VerifyEmail : AuthScreens

    @Serializable
    object VerifyPhoneNumber : AuthScreens

    @Serializable
    object VerifyOTP : AuthScreens

    @Serializable
    object CreatePassword : AuthScreens

}