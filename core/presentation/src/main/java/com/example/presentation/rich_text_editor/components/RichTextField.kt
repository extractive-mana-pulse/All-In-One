package com.example.presentation.rich_text_editor.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.allinone.core.presentation.R
import com.example.presentation.rich_text_editor.FormattingSpan
import com.example.presentation.rich_text_editor.RichTextEditorAction
import com.example.presentation.rich_text_editor.RichTextEditorState
import com.example.presentation.rich_text_editor.helper.applyFormattingToSelection
import com.example.presentation.rich_text_editor.helper.getCurrentFormatting
import com.example.presentation.rich_text_editor.helper.handleTextChange
import com.example.presentation.rich_text_editor.util.TextFontFamily

@Composable
internal fun RichTextField(
    state: RichTextEditorState,
    onAction: (RichTextEditorAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var formattingSpans by remember { mutableStateOf<List<FormattingSpan>>(emptyList()) }
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val montserratFamily = FontFamily(Font(R.font.montserrat_regular))
    val ptSerifFamily = FontFamily(Font(R.font.pt_serif_regular))

    val annotatedText = remember(textFieldValue.text, formattingSpans) {
        buildAnnotatedString {
            append(textFieldValue.text)
            formattingSpans.forEach { span ->
                val validStart = maxOf(0, span.start)
                val validEnd = minOf(span.end, textFieldValue.text.length)
                if (validStart < validEnd) {
                    addStyle(
                        style = SpanStyle(
                            fontWeight = if (span.isBold) FontWeight.Bold else FontWeight.Normal,
                            fontStyle = if (span.isItalic) FontStyle.Italic else FontStyle.Normal,
                            textDecoration = if (span.isUnderline) TextDecoration.Underline else TextDecoration.None,
                            color = span.color.color,
                            fontSize = span.fontSize.size,
                            fontFamily = when (span.fontFamily) {
                                TextFontFamily.Montserrat -> montserratFamily
                                TextFontFamily.PTSerif -> ptSerifFamily
                            }
                        ),
                        start = validStart,
                        end = validEnd
                    )
                }
            }
        }
    }

    BasicTextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            val oldValue = textFieldValue
            textFieldValue = newValue
            formattingSpans = handleTextChange(
                oldText = oldValue.text,
                newText = newValue.text,
                oldSelection = oldValue.selection,
                newSelection = newValue.selection,
                spans = formattingSpans,
                currentFormatting = getCurrentFormatting(state)
            )
        },
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { isFocused = it.isFocused }
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .padding(bottom = 80.dp),
        textStyle = TextStyle(
            fontSize = state.currentFontSize.size,
            fontFamily = FontFamily(Font(state.currentFontFamily.fontResource)),
            color = Color.Transparent/*state.currentColor.color*/,
        ),
        cursorBrush = SolidColor(Color.Black),
    ) { innerTextField ->
        Box(modifier = Modifier.fillMaxSize()) {
            if (textFieldValue.text.isEmpty() && !isFocused) {
                Text(
                    text = "Start writing...",
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Gray.copy(alpha = 0.5f),
                        fontFamily = FontFamily(Font(R.font.montserrat_medium))
                    )
                )
            }
            if (textFieldValue.text.isNotEmpty()) {
                Text(
                    text = annotatedText,
                    style = TextStyle(fontSize = state.currentFontSize.size)
                )
            }
            Box(/*modifier = Modifier.alpha(0.01f)*/) {
                innerTextField()
            }
        }
    }

    LaunchedEffect(textFieldValue.selection, formattingSpans) {
        if (textFieldValue.selection.collapsed && textFieldValue.text.isNotEmpty()) {
            val cursorPos = textFieldValue.selection.start
            val spanAtCursor = formattingSpans.firstOrNull { cursorPos > it.start && cursorPos <= it.end }
                ?: formattingSpans.lastOrNull { cursorPos == it.start }
            if (spanAtCursor != null) {
                onAction(RichTextEditorAction.UpdateToolbarFromCursor(
                    isBold = spanAtCursor.isBold,
                    isItalic = spanAtCursor.isItalic,
                    isUnderline = spanAtCursor.isUnderline,
                    color = spanAtCursor.color,
                    fontFamily = spanAtCursor.fontFamily,
                    fontSize = spanAtCursor.fontSize
                ))
            }
        }
    }

    LaunchedEffect(
        state.isCurrentlyBold, state.isCurrentlyItalic, state.isCurrentlyUnderline,
        state.currentColor, state.currentFontFamily, state.currentFontSize
    ) {
        if (!textFieldValue.selection.collapsed) {
            formattingSpans = applyFormattingToSelection(
                selection = textFieldValue.selection,
                spans = formattingSpans,
                formatting = getCurrentFormatting(state)
            )
        }
    }
}