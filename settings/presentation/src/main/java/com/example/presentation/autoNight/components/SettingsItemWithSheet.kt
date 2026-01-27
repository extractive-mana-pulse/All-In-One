package com.example.presentation.autoNight.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.allinone.core.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsItemWithSheet(
    title: String,
    description: String,
    icon: Painter? = null,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(),
            sheetState = sheetState,
            onDismissRequest = { showBottomSheet = false }
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.select_language),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold))
                    ),
                )
                RadioButtonSingleSelection()
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showBottomSheet = true }
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = title,
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
            },
            supportingContent = {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_medium)),
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                        lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                        letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    ),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            },
            leadingContent = {
                icon?.let {
                    Box(
                        modifier = Modifier.size(48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = it,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            },
        )
        HorizontalDivider(
            modifier = Modifier.padding(start = 80.dp, end = 16.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
    }
}