package com.example.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import com.example.allinone.R

@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    confirmText: String,
    dismissText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(
                icon,
                contentDescription = null
            )
        },
        title = {
            Text(
                text = dialogTitle,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_bold)),
                    fontWeight = MaterialTheme.typography.headlineSmall.fontWeight,
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    letterSpacing = MaterialTheme.typography.headlineSmall.letterSpacing,
                    lineHeight = MaterialTheme.typography.headlineSmall.lineHeight,
                    platformStyle = MaterialTheme.typography.headlineSmall.platformStyle,
                    textDirection = MaterialTheme.typography.headlineSmall.textDirection,
                )
            )
        },
        text = {
            Text(
                text = dialogText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_light)),
                    fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                    platformStyle = MaterialTheme.typography.bodyMedium.platformStyle,
                    textDirection = MaterialTheme.typography.bodyMedium.textDirection,
                )
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(
                    text = confirmText,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_bold)),
                        fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                        letterSpacing = MaterialTheme.typography.labelLarge.letterSpacing,
                        lineHeight = MaterialTheme.typography.labelLarge.lineHeight,
                        platformStyle = MaterialTheme.typography.labelLarge.platformStyle,
                        textDirection = MaterialTheme.typography.labelLarge.textDirection,
                    )
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(
                    text = dismissText,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_bold)),
                        fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                        letterSpacing = MaterialTheme.typography.labelLarge.letterSpacing,
                        lineHeight = MaterialTheme.typography.labelLarge.lineHeight,
                        platformStyle = MaterialTheme.typography.labelLarge.platformStyle,
                        textDirection = MaterialTheme.typography.labelLarge.textDirection,
                    )
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun CustomAlertDialogPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        CustomAlertDialog(
            onDismissRequest = {},
            onConfirmation = {},
            dialogTitle = "Lorem Ipsum",
            dialogText = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.\n" +
                    "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. ",
            icon = Icons.Default.Info, // Change to your desired icon,
            confirmText = "Confirm",
            dismissText = "Dismiss"
        )
    }
}