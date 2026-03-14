package com.example.presentation.rich_text_editor.helper

import androidx.compose.ui.text.TextRange
import com.example.presentation.rich_text_editor.FormattingSpan
import com.example.presentation.rich_text_editor.FormattingStyle

fun handleTextChange(
    oldText: String,
    newText: String,
    oldSelection: TextRange,
    newSelection: TextRange,
    spans: List<FormattingSpan>,
    currentFormatting: FormattingStyle
): List<FormattingSpan> {
    val oldLength = oldText.length
    val newLength = newText.length
    val diff = newLength - oldLength

    return when {
        diff > 0 -> handleTextInsertion(
            insertPosition = oldSelection.start,
            insertedLength = diff,
            spans = spans,
            currentFormatting = currentFormatting,
        )

        diff < 0 -> handleTextDeletion(
            deleteStart = newSelection.start,
            deletedLength = -diff,
            spans = spans
        )

        else -> spans
    }
}