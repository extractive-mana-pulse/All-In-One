package com.example.allinone.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun PrimaryButton(
    onClick: () -> Unit,
    enabled: Boolean = false,
    modifier: Modifier = Modifier,
    colors : ButtonColors = ButtonDefaults.buttonColors(),
    shape: RoundedCornerShape = RoundedCornerShape(0.dp),
    contentOfTheButton: @Composable () -> Unit = {}
) {

    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = { onClick() },
        enabled = enabled,
        shape = shape,
        colors = colors
    ) {
        contentOfTheButton()
    }
}