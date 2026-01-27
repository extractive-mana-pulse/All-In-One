package com.example.allinone.navigation.screen

import kotlinx.serialization.Serializable

@Serializable
sealed interface ProfileScreens {

    @Serializable
    object Profile : ProfileScreens

    @Serializable
    object EditProfile : ProfileScreens
}