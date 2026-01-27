package com.example.allinone.navigation.screen

import kotlinx.serialization.Serializable

@Serializable
sealed interface CodeLabScreens {

    @Serializable
    object ComposeArticleScreen : CodeLabScreens

    @Serializable
    object TaskManagerScreen : CodeLabScreens

    @Serializable
    object Quadrant : CodeLabScreens

    @Serializable
    object TipCalculator : CodeLabScreens

    @Serializable
    object Lemonade : CodeLabScreens

    @Serializable
    object ArtSpace : CodeLabScreens

    @Serializable
    object BusinessCard : CodeLabScreens

    @Serializable
    object DiceRoller : CodeLabScreens

    @Serializable
    object Woof : CodeLabScreens
}