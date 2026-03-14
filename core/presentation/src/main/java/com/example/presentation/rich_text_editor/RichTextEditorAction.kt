package com.example.presentation.rich_text_editor

import com.example.presentation.rich_text_editor.util.TextColor
import com.example.presentation.rich_text_editor.util.TextFontFamily
import com.example.presentation.rich_text_editor.util.TextSize

sealed interface RichTextEditorAction {
    data object OnBoldClick : RichTextEditorAction
    data object OnItalicClick : RichTextEditorAction
    data object OnUnderlineClick : RichTextEditorAction
    data object OnColorClick : RichTextEditorAction
    data object OnColorDropdownDismiss : RichTextEditorAction
    data class OnColorChange(val value: com.example.presentation.rich_text_editor.util.TextColor) : RichTextEditorAction
    data object OnFontFamilyClick : RichTextEditorAction
    data object OnFontFamilyDropdownDismiss : RichTextEditorAction
    data class OnFontFamilyChange(val value: com.example.presentation.rich_text_editor.util.TextFontFamily) : RichTextEditorAction
    data object OnFontSizeClick : RichTextEditorAction
    data object OnFontSizeDropdownDismiss : RichTextEditorAction
    data class OnFontSizeChange(val value: com.example.presentation.rich_text_editor.util.TextSize) : RichTextEditorAction
    data object OnResetClick : RichTextEditorAction
    data class UpdateToolbarFromCursor(
        val isBold: Boolean,
        val isItalic: Boolean,
        val isUnderline: Boolean,
        val color: com.example.presentation.rich_text_editor.util.TextColor,
        val fontFamily: com.example.presentation.rich_text_editor.util.TextFontFamily,
        val fontSize: com.example.presentation.rich_text_editor.util.TextSize,
    ) : RichTextEditorAction
}