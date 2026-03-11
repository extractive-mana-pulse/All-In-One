package com.example.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.allinone.core.presentation.R
import com.example.domain.model.Codelab

@Composable
internal fun CodelabListItem(
    modifier: Modifier = Modifier,
    codelab: Codelab,
    onNavigateToDetailWithId: (Int) -> Unit,
    isLastItem: Boolean = false
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onNavigateToDetailWithId(codelab.id.toIntOrNull() ?: 0) }
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = codelab.imageUrl.ifEmpty { R.drawable.compose_logo },
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = codelab.title,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_bold)),
                    )
                )
                Text(
                    text = codelab.subtitle,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_light)),
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        if (!isLastItem) {
            HorizontalDivider(color = MaterialTheme.colorScheme.surface)
        }
    }
}