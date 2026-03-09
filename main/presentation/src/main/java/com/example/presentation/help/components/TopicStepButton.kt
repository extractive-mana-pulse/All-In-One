package com.example.presentation.help.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.allinone.core.presentation.R
import com.example.presentation.help.FEEDBACK_TOPICS

@Composable
internal fun TopicStepButton(
    selected: String?,
    onSelect: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    StepButton(
        label = selected ?: "Topic",
        iconVector = painterResource(R.drawable.outline_check_24),
        isComplete = selected != null,
        enabled = true,
        onClick = { expanded = true }
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        FEEDBACK_TOPICS.forEach { topic ->
            DropdownMenuItem(
                text = { Text(topic) },
                onClick = { onSelect(topic); expanded = false },
                leadingIcon = if (topic == selected) {
                    { Icon(painter = painterResource(R.drawable.outline_check_24), contentDescription = null, modifier = Modifier.size(16.dp)) }
                } else null
            )
        }
    }
}