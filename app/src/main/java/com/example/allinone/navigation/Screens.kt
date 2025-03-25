package com.example.allinone.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens(val route: String) {

    // implemented
    @Serializable
    object ComposeArticleScreen : Screens("compose_article")

    // not yet
    @Serializable
    object TaskManagerScreen : Screens("task_manager")

    // implemented
    @Serializable
    object Quadrant : Screens("quadrant")

    // implemented
    @Serializable
    object TipCalculator : Screens("tip_calculator")

    // implemented
    @Serializable
    object Lemonade : Screens("lemonade")

    // not yet
    @Serializable
    object ArtSpace : Screens("art_space")

}

@Serializable
sealed class HomeScreens(val route: String) {

    @Serializable
    object Home : HomeScreens("home_screen")

    @Serializable
    data class DetailsScreen(val id: Int) : HomeScreens("details_screen")

    @Serializable
    object Help : HomeScreens("help_and_feedback")
}

@Serializable
sealed class SettingsScreens(val route: String) {

    @Serializable
    object Settings : SettingsScreens("settings")

    @Serializable
    object Night: SettingsScreens("night_mode")

    @Serializable
    object PowerSaving: SettingsScreens("power_saving")

    @Serializable
    object AdaptiveMode: SettingsScreens("adaptive_mode")

    @Serializable
    object ScheduledMode: SettingsScreens("scheduled_mode")
}

@Serializable
sealed class ProfileScreens(val route: String) {

    @Serializable
    object Profile : ProfileScreens("profile")

    @Serializable
    object EditProfile : ProfileScreens("edit_profile")
}

sealed class BlogsScreens(val route: String) {

}

sealed class CodeLabsScreens(val route: String) {

}