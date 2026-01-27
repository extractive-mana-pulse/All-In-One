package com.example.presentation.autoNight.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.example.allinone.core.presentation.R

@PreviewLightDark
@Composable
internal fun SettingWithSwitchPreview() {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_read_more_24),
                        contentDescription = null,
                        modifier = Modifier.size(36.dp),
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Reading Mode",
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

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Reading mode mainly used for reading articles. it\\'s decrease a light-blue color and make it less pain for your eyes.",
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
                }

                Box(
                    modifier = Modifier.padding(start = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Switch(
                        checked = true,
                        onCheckedChange = { checked -> checked },
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier.padding(start = 80.dp, end = 16.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )
        }
    }
}