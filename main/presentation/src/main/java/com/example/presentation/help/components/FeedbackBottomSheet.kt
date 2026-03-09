package com.example.presentation.help.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.allinone.core.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FeedbackBottomSheet(onDismiss: () -> Unit) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var selectedTopic by remember { mutableStateOf<String?>(null) }
    var messageContext by remember { mutableStateOf("") }
    var showContextDialog by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Send us feedback 💬",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "We'd love to hear what's on your mind.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(28.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    TopicStepButton(
                        selected = selectedTopic,
                        onSelect = { selectedTopic = it }
                    )
                }

                StepArrow(enabled = selectedTopic != null)

                StepButton(
                    modifier = Modifier.weight(1f),
                    label = if (messageContext.isBlank()) "Context" else "Edited ✓",
                    iconVector = painterResource(R.drawable.outline_check_24),
                    isComplete = messageContext.isNotBlank(),
                    enabled = selectedTopic != null,
                    onClick = { showContextDialog = true }
                )

                StepArrow(enabled = messageContext.isNotBlank())

                StepButton(
                    modifier = Modifier.weight(1f),
                    label = "Send",
                    iconVector = painterResource(R.drawable.outline_arrow_forward_24),
                    isComplete = false,
                    enabled = selectedTopic != null && messageContext.isNotBlank(),
                    containerColor = if (selectedTopic != null && messageContext.isNotBlank())
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (selectedTopic != null && messageContext.isNotBlank())
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant,
                    onClick = {
                        openGmail(
                            context = context,
                            subject = "Feedback: $selectedTopic",
                            body = messageContext
                        )
                        onDismiss()
                    }
                )
            }

            Spacer(Modifier.height(16.dp))
            Text(
                text = when {
                    selectedTopic == null -> "Start by choosing a topic →"
                    messageContext.isBlank() -> "Now describe the issue →"
                    else -> "Ready to send! Tap Send ✓"
                },
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

    if (showContextDialog) {
        ContextDialog(
            initial = messageContext,
            onConfirm = { messageContext = it; showContextDialog = false },
            onDismiss = { showContextDialog = false }
        )
    }
}