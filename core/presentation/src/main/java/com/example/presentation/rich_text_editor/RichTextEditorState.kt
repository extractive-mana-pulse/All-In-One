package com.example.presentation.rich_text_editor

import com.example.presentation.rich_text_editor.util.TextColor
import com.example.presentation.rich_text_editor.util.TextFontFamily
import com.example.presentation.rich_text_editor.util.TextSize

data class RichTextEditorState(
    val isCurrentlyBold: Boolean = false,
    val isCurrentlyItalic: Boolean = false,
    val isCurrentlyUnderline: Boolean = false,
    val currentColor: com.example.presentation.rich_text_editor.util.TextColor = _root_ide_package_.com.example.presentation.rich_text_editor.util.TextColor.Black,
    val currentFontFamily: com.example.presentation.rich_text_editor.util.TextFontFamily = _root_ide_package_.com.example.presentation.rich_text_editor.util.TextFontFamily.Montserrat,
    val currentFontSize: com.example.presentation.rich_text_editor.util.TextSize = _root_ide_package_.com.example.presentation.rich_text_editor.util.TextSize.Medium,
    val isSelectColorDropdownExpanded: Boolean = false,
    val isSelectFontFamilyDropdownExpanded: Boolean = false,
    val isSelectFontSizeDropdownExpanded: Boolean = false,
)