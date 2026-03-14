package com.example.presentation.rich_text_editor.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
internal fun ToolbarIconButton(
    icon: Painter,
    isActive: Boolean,
    onClick: () -> Unit,
    iconModifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .size(34.dp)
            .clip(RoundedCornerShape(6.dp))
            .clickable(onClick = onClick)
            .background(if (isActive) Color(0xFFEEEEEE) else Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = if (isActive) Color(0xFF111111) else Color(0xFF555555),
            modifier = iconModifier
        )
    }
}