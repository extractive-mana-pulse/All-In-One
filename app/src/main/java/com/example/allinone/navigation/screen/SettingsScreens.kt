package com.example.allinone.navigation.screen

import kotlinx.serialization.Serializable

@Serializable
sealed interface SettingsScreens {

    @Serializable
    object Settings : SettingsScreens

    @Serializable
    object Night: SettingsScreens

    @Serializable
    object PowerSaving: SettingsScreens

    @Serializable
    object AdaptiveMode: SettingsScreens

    @Serializable
    object ScheduledMode: SettingsScreens

    @Serializable
    object Temperature: SettingsScreens

    @Serializable
    object AllSensors: SettingsScreens
}