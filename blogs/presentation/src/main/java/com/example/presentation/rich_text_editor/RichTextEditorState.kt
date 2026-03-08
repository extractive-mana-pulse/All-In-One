package com.example.presentation.rich_text_editor

import com.example.presentation.rich_text_editor.util.TextColor
import com.example.presentation.rich_text_editor.util.TextFontFamily
import com.example.presentation.rich_text_editor.util.TextSize

data class RichTextEditorState(
    val isCurrentlyBold: Boolean = false,
    val isCurrentlyItalic: Boolean = false,
    val isCurrentlyUnderline: Boolean = false,
    val currentColor: TextColor = TextColor.Black,
    val currentFontFamily: TextFontFamily = TextFontFamily.Montserrat,
    val currentFontSize: TextSize = TextSize.Medium,
    val isSelectColorDropdownExpanded: Boolean = false,
    val isSelectFontFamilyDropdownExpanded: Boolean = false,
    val isSelectFontSizeDropdownExpanded: Boolean = false,
)