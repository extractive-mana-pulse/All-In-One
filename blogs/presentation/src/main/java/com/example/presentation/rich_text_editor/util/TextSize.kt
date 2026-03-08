package com.example.presentation.rich_text_editor.util

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

enum class TextSize (
    val size: TextUnit
) {
    Small(12.sp),
    Medium(18.sp),
    Large(34.sp);

    fun label(): String {
        return when (this) {
            Small -> "Small"
            Medium -> "Medium"
            Large -> "Large"
        }
    }
}