package com.example.allinone.navigation.util

import com.example.allinone.navigation.screen.CodeLabScreens

internal fun String.navigateByTitle(
    onNavigate: (route: Any, shouldStopTimer: Boolean) -> Unit,
    onError: () -> Unit
) {
    when (this) {
        "Compose article" -> onNavigate(CodeLabScreens.ComposeArticleScreen, false)
        "Quadrant" -> onNavigate(CodeLabScreens.Quadrant, false)
        "Tip Calculator" -> onNavigate(CodeLabScreens.TipCalculator, false)
        "Art Space App" -> onNavigate(CodeLabScreens.ArtSpace, true)
        "Lemonade" -> onNavigate(CodeLabScreens.Lemonade, true)
        "Task Manager" -> onNavigate(CodeLabScreens.TaskManagerScreen, true)
        "Art Space" -> onNavigate(CodeLabScreens.ArtSpace, true)
        "Business card" -> onNavigate(CodeLabScreens.BusinessCard, false)
        "Dice Roller" -> onNavigate(CodeLabScreens.DiceRoller, false)
        "Woof" -> onNavigate(CodeLabScreens.Woof, false)
        else -> onError()
    }
}