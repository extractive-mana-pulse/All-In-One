package com.example.allinone.core.util.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.allinone.R


@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
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
                text = dialogTitle
            )
        },
        text = {
            Text(
                text = dialogText
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
                    text = stringResource(R.string.comfirm)
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
                    text = stringResource(R.string.dismiss)
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
            dialogTitle = "Dialog Title",
            dialogText = "This is a sample dialog text.",
            icon = Icons.Default.Info // Change to your desired icon
        )
    }
}