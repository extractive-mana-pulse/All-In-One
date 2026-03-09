package com.example.presentation.help.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.allinone.core.presentation.R

@Composable
internal fun StepArrow(enabled: Boolean) {
    val alpha by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.25f,
        label = "arrowAlpha"
    )
    Icon(
        painter = painterResource(R.drawable.outline_arrow_forward_24),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.outline.copy(alpha = alpha),
        modifier = Modifier.size(16.dp)
    )
}
