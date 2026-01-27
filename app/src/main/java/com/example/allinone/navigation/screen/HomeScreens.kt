package com.example.allinone.navigation.screen

import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeScreens {

    @Serializable
    object Home : HomeScreens

    @Serializable
    data class DetailsScreen(val id: Int) : HomeScreens

    @Serializable
    object Help : HomeScreens

    @Serializable
    data class SectionScreen(val id: Int) : HomeScreens
}
