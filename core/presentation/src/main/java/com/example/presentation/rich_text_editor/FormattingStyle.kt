package com.example.presentation.rich_text_editor

import com.example.presentation.rich_text_editor.util.TextColor
import com.example.presentation.rich_text_editor.util.TextFontFamily
import com.example.presentation.rich_text_editor.util.TextSize

data class FormattingStyle(
    val isBold: Boolean,
    val isItalic: Boolean,
    val isUnderline: Boolean,
    val color: com.example.presentation.rich_text_editor.util.TextColor,
    val fontFamily: com.example.presentation.rich_text_editor.util.TextFontFamily,
    val fontSize: com.example.presentation.rich_text_editor.util.TextSize
)