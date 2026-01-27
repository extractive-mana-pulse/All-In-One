package com.example.presentation.deviceTemp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.allinone.core.presentation.R
import com.example.domain.TemperatureData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AboutWithSheet(
    sensorNames: String,
    onDismiss: () -> Unit,
    tempData: TemperatureData
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    ModalBottomSheet(
        modifier = Modifier.fillMaxWidth(),
        sheetState = sheetState,
        onDismissRequest = { onDismiss() }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.about_device_temp),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold))
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = stringResource(R.string.about_device_temp_desc),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular)),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                    lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                    letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                    textAlign = TextAlign.Justify
                )
            )

            Text(
                text = "Sensor: $sensorNames, is used to measure temperature.",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_extra_bold)),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                    lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                    letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                    textAlign = TextAlign.Justify
                )
            )
            Text(
                text = tempData.toString(),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_extra_bold)),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                    lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                    letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                    textAlign = TextAlign.Justify
                )
            )

            Text(
                text = stringResource(R.string.note_device_temp_about_sheet_info),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_extra_bold)),
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                    lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                    letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.error
                )
            )

            OutlinedButton(
                onClick = { onDismiss() },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.End)
            ) {
                Text(
                    text = stringResource(R.string.close)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}