package com.example.presentation.art_space_app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.presentation.components.PrimaryButton

@Composable
internal fun CustomPointerButtons(currentIndex: MutableState<Int>, imageCount: Int) {
    Row(
        modifier = Modifier
            .padding(top = 40.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom,
    ) {
        PrimaryButton(
            onClick = {
                if (currentIndex.value > 0) {
                    currentIndex.value -= 1
                }
            },
            modifier = Modifier.weight(0.5f).padding(end = 24.dp)
        ) {
            Text(text = "Previous")
        }
        PrimaryButton(
            onClick = {
                if (currentIndex.value < imageCount - 1) {
                    currentIndex.value += 1
                }
            },
            modifier = Modifier.weight(0.5f).padding(start = 24.dp)
        ) {
            Text(text = "Next")
        }
    }
}