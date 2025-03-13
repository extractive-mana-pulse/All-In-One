package com.example.allinone.screens

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens(val route: String) {

    @Serializable
    object Home : Screens("home")

    @Serializable
    object Profile : Screens("profile")

    @Serializable
    object EditProfile : Screens("edit_profile")

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
    object Settings : Screens("settings")

    @Serializable
    object ArtSpace : Screens("art_space")

    @Serializable
    object Night: Screens("night_mode")

    @Serializable
    object LanguageSheet: Screens("language_sheet")
}