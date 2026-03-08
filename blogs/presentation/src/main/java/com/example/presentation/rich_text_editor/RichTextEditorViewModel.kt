package com.example.presentation.rich_text_editor

import androidx.lifecycle.ViewModel
import com.example.presentation.rich_text_editor.util.TextColor
import com.example.presentation.rich_text_editor.util.TextFontFamily
import com.example.presentation.rich_text_editor.util.TextSize
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RichTextEditorViewModel : ViewModel() {

    private val _state = MutableStateFlow(RichTextEditorState())
    val state = _state.asStateFlow()

    fun onAction(action: RichTextEditorAction) {
        when (action) {
            RichTextEditorAction.OnBoldClick ->
                _state.update { it.copy(isCurrentlyBold = !it.isCurrentlyBold) }

            RichTextEditorAction.OnItalicClick ->
                _state.update { it.copy(isCurrentlyItalic = !it.isCurrentlyItalic) }

            RichTextEditorAction.OnUnderlineClick ->
                _state.update { it.copy(isCurrentlyUnderline = !it.isCurrentlyUnderline) }

            RichTextEditorAction.OnColorClick ->
                _state.update { it.copy(isSelectColorDropdownExpanded = true) }

            RichTextEditorAction.OnColorDropdownDismiss ->
                _state.update { it.copy(isSelectColorDropdownExpanded = false) }

            is RichTextEditorAction.OnColorChange ->
                _state.update { it.copy(currentColor = action.value, isSelectColorDropdownExpanded = false) }

            RichTextEditorAction.OnFontFamilyClick ->
                _state.update { it.copy(isSelectFontFamilyDropdownExpanded = true) }

            RichTextEditorAction.OnFontFamilyDropdownDismiss ->
                _state.update { it.copy(isSelectFontFamilyDropdownExpanded = false) }

            is RichTextEditorAction.OnFontFamilyChange ->
                _state.update { it.copy(currentFontFamily = action.value, isSelectFontFamilyDropdownExpanded = false) }

            RichTextEditorAction.OnFontSizeClick ->
                _state.update { it.copy(isSelectFontSizeDropdownExpanded = true) }

            RichTextEditorAction.OnFontSizeDropdownDismiss ->
                _state.update { it.copy(isSelectFontSizeDropdownExpanded = false) }

            is RichTextEditorAction.OnFontSizeChange ->
                _state.update { it.copy(currentFontSize = action.value, isSelectFontSizeDropdownExpanded = false) }

            RichTextEditorAction.OnResetClick ->
                _state.update {
                    it.copy(
                        isCurrentlyBold = false,
                        isCurrentlyItalic = false,
                        isCurrentlyUnderline = false,
                        currentColor = TextColor.Black,
                        currentFontFamily = TextFontFamily.Montserrat,
                        currentFontSize = TextSize.Medium,
                    )
                }

            is RichTextEditorAction.UpdateToolbarFromCursor ->
                _state.update {
                    it.copy(
                        isCurrentlyBold = action.isBold,
                        isCurrentlyItalic = action.isItalic,
                        isCurrentlyUnderline = action.isUnderline,
                        currentColor = action.color,
                        currentFontFamily = action.fontFamily,
                        currentFontSize = action.fontSize,
                    )
                }
        }
    }
}