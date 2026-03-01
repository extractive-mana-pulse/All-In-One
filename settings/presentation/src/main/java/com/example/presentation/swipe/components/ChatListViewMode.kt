package com.example.presentation.swipe.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.ChatListViewMode

@Composable
internal fun ChatListViewSelector(
    selectedMode: ChatListViewMode,
    onModeSelected: (ChatListViewMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val telegramBlue = Color(0xFF2AABEE)

    SectionCard(title = "Chat list view", modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ChatListViewOption(
                label = "Two lines",
                lineCount = 2,
                isSelected = selectedMode == ChatListViewMode.TWO_LINES,
                accentColor = telegramBlue,
                onClick = { onModeSelected(ChatListViewMode.TWO_LINES) },
                modifier = Modifier.weight(1f)
            )
            ChatListViewOption(
                label = "Three lines",
                lineCount = 3,
                isSelected = selectedMode == ChatListViewMode.THREE_LINES,
                accentColor = telegramBlue,
                onClick = { onModeSelected(ChatListViewMode.THREE_LINES) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ChatListViewOption(
    label: String,
    lineCount: Int,
    isSelected: Boolean,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = if (isSelected) accentColor else Color(0xFFE0E0E0)
    val backgroundColor = Color.White

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(backgroundColor)
                .border(
                    BorderStroke(
                        width = if (isSelected) 2.dp else 1.dp,
                        color = borderColor
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
                .clickable(onClick = onClick)
                .padding(12.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                repeat(lineCount) {
                    MockChatRow(accentColor = accentColor)
                }
            }

            // Radio indicator
            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = accentColor,
                    unselectedColor = Color(0xFFBDBDBD)
                ),
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = label,
            fontSize = 13.sp,
            color = Color(0xFF222222)
        )
    }
}

@Composable
private fun MockChatRow(accentColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Avatar placeholder
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(Color(0xFFD0D0D0))
        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Box(
                modifier = Modifier
                    .height(7.dp)
                    .fillMaxWidth(0.55f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFD0D0D0))
            )
            Box(
                modifier = Modifier
                    .height(6.dp)
                    .fillMaxWidth(0.75f)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFE8E8E8))
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0F2F5)
@Composable
fun ChatSettingsPreview() {
    var viewMode by remember { mutableStateOf(ChatListViewMode.TWO_LINES) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ChatListViewSelector(
            selectedMode = viewMode,
            onModeSelected = { viewMode = it }
        )
    }
}