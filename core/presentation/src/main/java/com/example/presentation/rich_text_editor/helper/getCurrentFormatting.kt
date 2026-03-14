package com.example.presentation.rich_text_editor.helper

import com.example.presentation.rich_text_editor.FormattingStyle
import com.example.presentation.rich_text_editor.RichTextEditorState

fun getCurrentFormatting(state: RichTextEditorState): FormattingStyle {
    return FormattingStyle(
        isBold = state.isCurrentlyBold,
        isItalic = state.isCurrentlyItalic,
        isUnderline = state.isCurrentlyUnderline,
        color = state.currentColor,
        fontFamily = state.currentFontFamily,
        fontSize = state.currentFontSize
    )
}