package com.example.presentation.rich_text_editor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.allinone.core.presentation.R
import com.example.presentation.rich_text_editor.components.RichTextField
import com.example.presentation.rich_text_editor.components.RichTextToolbar

@Composable
fun RichTextEditorRoot(
    viewModel: com.example.presentation.rich_text_editor.RichTextEditorViewModel = viewModel(),
    onNavigateUp: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    _root_ide_package_.com.example.presentation.rich_text_editor.RichTextEditorScreen(
        state = state,
        onAction = viewModel::onAction,
        onNavigateUp = onNavigateUp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RichTextEditorScreen(
    state: com.example.presentation.rich_text_editor.RichTextEditorState,
    onAction: (com.example.presentation.rich_text_editor.RichTextEditorAction) -> Unit,
    onNavigateUp: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Rich Text Editor",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp){
                        Icon(
                            painter = painterResource(R.drawable.outline_arrow_back_24),
                            contentDescription = null
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
        ) {
            _root_ide_package_.com.example.presentation.rich_text_editor.components.RichTextField(
                state = state,
                onAction = onAction,
                modifier = Modifier.fillMaxSize()
            )
            _root_ide_package_.com.example.presentation.rich_text_editor.components.RichTextToolbar(
                state = state,
                onAction = onAction,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            )
        }
    }
}