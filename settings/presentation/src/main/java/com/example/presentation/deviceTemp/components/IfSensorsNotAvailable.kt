package com.example.presentation.deviceTemp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.allinone.core.presentation.R

@Composable
internal fun IfSensorsNotAvailable() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.warning_24px),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.no_temp_sensor_available),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular)),
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                    lineHeight = MaterialTheme.typography.bodyLarge.lineHeight,
                    letterSpacing = MaterialTheme.typography.bodyLarge.letterSpacing,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.error
                ),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.device_sensors_info),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular)),
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight,
                    letterSpacing = MaterialTheme.typography.bodyMedium.letterSpacing,
                    textAlign = TextAlign.Justify
                ),
            )
        }
    }
}
