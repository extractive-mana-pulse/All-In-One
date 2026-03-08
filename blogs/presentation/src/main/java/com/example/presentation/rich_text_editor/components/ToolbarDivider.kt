package com.example.presentation.rich_text_editor.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun ToolbarDivider() {
    VerticalDivider(
        thickness = 1.dp,
        color = Color(0xFFDDDDDD),
        modifier = Modifier
            .height(22.dp)
            .padding(horizontal = 8.dp)
    )
}