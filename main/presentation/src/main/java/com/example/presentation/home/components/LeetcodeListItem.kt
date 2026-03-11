package com.example.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.allinone.core.presentation.R
import com.example.domain.model.Leetcode

@Composable
fun LeetcodeListItem(
    modifier: Modifier = Modifier,
    algorithm: Leetcode,
    onItemClick: () -> Unit,
    isLastItem: Boolean = false
) {
    val difficultyColor = when (algorithm.difficulty.lowercase()) {
        "easy" -> Color(0xFF00B8A3)
        "medium" -> Color(0xFFFFC01E)
        "hard" -> Color(0xFFFF375F)
        else -> MaterialTheme.colorScheme.outline
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick() }
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = algorithm.title,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_bold)),
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = algorithm.category,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_light)),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(difficultyColor.copy(alpha = 0.15f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = algorithm.difficulty,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = difficultyColor,
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_bold)),
                    )
                )
            }
        }

        if (!isLastItem) {
            HorizontalDivider(color = MaterialTheme.colorScheme.surface)
        }
    }
}