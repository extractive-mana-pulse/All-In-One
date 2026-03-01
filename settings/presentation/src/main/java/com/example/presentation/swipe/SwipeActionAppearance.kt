package com.example.presentation.swipe

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class SwipeActionAppearance(
    @DrawableRes val iconRes: Int,
    val backgroundColor: Color
)