package com.example.presentation.ui.themes

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val text = Color(0xFF0D1040)
val strokeDark = Color(0xFFBFC1E2)
val strokeLight = Color(0xFFFFFFFF)
val bg = Color(0xFFFFFFFF)
val evergreenWish = Color(0xFF244008)
val frostyLight = Color(0xFFFCFCFF)

val goldenEveGradient = Brush.radialGradient(
    colors = listOf(
        Color(0xFFD77C20),
        Color(0xFFCA5819),
        Color(0xFF943112)
    )
)

val snowyNightGradient = Brush.radialGradient(
    colors = listOf(
        Color(0xFF7D87F4),
        Color(0xFF5A63ED),
        Color(0xFF2746AE)
    )
)