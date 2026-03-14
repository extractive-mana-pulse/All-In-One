package com.example.presentation.rich_text_editor

data class FormattingSpan(
    val start: Int,
    val end: Int,
    val isBold: Boolean = false,
    val isItalic: Boolean = false,
    val isUnderline: Boolean = false,
    val color: com.example.presentation.rich_text_editor.util.TextColor,
    val fontFamily: com.example.presentation.rich_text_editor.util.TextFontFamily,
    val fontSize: com.example.presentation.rich_text_editor.util.TextSize
)