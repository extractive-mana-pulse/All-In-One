package com.example.presentation.rich_text_editor

import androidx.lifecycle.ViewModel
import com.example.presentation.rich_text_editor.util.TextColor
import com.example.presentation.rich_text_editor.util.TextFontFamily
import com.example.presentation.rich_text_editor.util.TextSize
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RichTextEditorViewModel : ViewModel() {

    private val _state = MutableStateFlow(_root_ide_package_.com.example.presentation.rich_text_editor.RichTextEditorState())
    val state = _state.asStateFlow()

    fun onAction(action: com.example.presentation.rich_text_editor.RichTextEditorAction) {
        when (action) {
            _root_ide_package_.com.example.presentation.rich_text_editor.RichTextEditorAction.OnBoldClick ->
                _state.update { it.copy(isCurrentlyBold = !it.isCurrentlyBold) }

            _root_ide_package_.com.example.presentation.rich_text_editor.RichTextEditorAction.OnItalicClick ->
                _state.update { it.copy(isCurrentlyItalic = !it.isCurrentlyItalic) }

            _root_ide_package_.com.example.presentation.rich_text_editor.RichTextEditorAction.OnUnderlineClick ->
                _state.update { it.copy(isCurrentlyUnderline = !it.isCurrentlyUnderline) }

            _root_ide_package_.com.example.presentation.rich_text_editor.RichTextEditorAction.OnColorClick ->
                _state.update { it.copy(isSelectColorDropdownExpanded = true) }

            _root_ide_package_.com.example.presentation.rich_text_editor.RichTextEditorAction.OnColorDropdownDismiss ->
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