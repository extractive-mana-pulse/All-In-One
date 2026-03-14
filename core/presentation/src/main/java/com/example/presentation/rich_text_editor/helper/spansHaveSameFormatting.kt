package com.example.presentation.rich_text_editor.helper

import com.example.presentation.rich_text_editor.FormattingSpan

fun spansHaveSameFormatting(span1: FormattingSpan, span2: FormattingSpan): Boolean {
    return span1.isBold == span2.isBold &&
            span1.isItalic == span2.isItalic &&
            span1.isUnderline == span2.isUnderline &&
            span1.color == span2.color &&
            span1.fontFamily == span2.fontFamily &&
            span1.fontSize == span2.fontSize
}
