package com.example.presentation.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun AllInOneTextButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    style: TextStyle
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = style
        )
    }
}