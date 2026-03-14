package com.example.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.presentation.rich_text_editor.RichTextEditorViewModel
import com.example.presentation.rich_text_editor.RteViewModel
import com.example.presentation.rich_text_editor.components.RichTextField

@Composable
fun RteAwareTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    // Pass your normal TextField here as the fallback
    normalTextField: @Composable () -> Unit,
    // Optional: bring your own RTE ViewModel, or let it create one per screen
) {
    val rteViewModel: RteViewModel = hiltViewModel()
    val isRteEnabled by rteViewModel.isRteEnabled.collectAsStateWithLifecycle()

    val richTextViewModel: RichTextEditorViewModel = viewModel()
    val state by richTextViewModel.state.collectAsStateWithLifecycle()


    if (isRteEnabled) {
        RichTextField(
            state = state,
            onAction = richTextViewModel::onAction,
            modifier = modifier // ← honours the caller's size/shape
        )
    } else {
        normalTextField()
    }
}