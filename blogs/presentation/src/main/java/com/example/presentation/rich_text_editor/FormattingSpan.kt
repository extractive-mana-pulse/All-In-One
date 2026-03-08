package com.example.presentation.rich_text_editor

import com.example.presentation.rich_text_editor.util.TextColor
import com.example.presentation.rich_text_editor.util.TextFontFamily
import com.example.presentation.rich_text_editor.util.TextSize

data class FormattingSpan(
    val start: Int,
    val end: Int,
    val isBold: Boolean = false,
    val isItalic: Boolean = false,
    val isUnderline: Boolean = false,
    val color: TextColor,
    val fontFamily: TextFontFamily,
    val fontSize: TextSize
)