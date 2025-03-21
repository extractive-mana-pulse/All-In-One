package com.example.allinone.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens(val route: String) {

    @Serializable
    object ComposeArticleScreen : Screens("compose_article")

    @Serializable
    object TaskManagerScreen : Screens("task_manager")

    @Serializable
    object Quadrant : Screens("quadrant")

    @Serializable
    object TipCalculator : Screens("tip_calculator")

    @Serializable
    object Lemonade : Screens("lemonade")

    @Serializable
    object ArtSpace : Screens("art_space")

}

sealed class HomeScreens(val route: String) {

    @Serializable
    object Home : Screens("home")

    @Serializable
    object DetailsScreen : Screens("details_screen")

    @Serializable
    object Help : Screens("help_and_feedback")
}

sealed class SettingsScreens(val route: String) {

    @Serializable
    object Settings : Screens("settings")

    @Serializable
    object Night: Screens("night_mode")

    @Serializable
    object PowerSaving: Screens("power_saving")

    @Serializable
    object AdaptiveMode: Screens("adaptive_mode")
}

sealed class ProfileScreens(val route: String) {
    @Serializable
    object Profile : Screens("profile")

    @Serializable
    object EditProfile : Screens("edit_profile")
}

sealed class BlogsScreens(val route: String) {

}

sealed class CodeLabsScreens(val route: String) {

}