package com.example.presentation.autoNight.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.allinone.core.presentation.R

@Composable
internal fun SettingsItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    icon: Painter? = null,
    isLast: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Box(
                modifier = Modifier.size(28.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = it,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
        }

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold)),
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Icon(
            painter = painterResource(R.drawable.outline_arrow_back_24), // rotate via graphicsLayer or use a forward arrow resource
            contentDescription = null,
            modifier = Modifier
                .size(18.dp)
                .padding(start = 4.dp),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f)
        )
    }

    if (!isLast) {
        HorizontalDivider(
            modifier = Modifier.padding(start = if (icon != null) 58.dp else 16.dp, end = 0.dp),
            thickness = 0.5.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
        )
    }
}