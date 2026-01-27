package com.example.presentation.deviceTemp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.allinone.core.presentation.R
import com.example.domain.TemperatureData

@Composable
fun TemperatureStatus(tempData: TemperatureData) {
    val backgroundColor = when {
        tempData.temperature < 10 -> Color(37,111,255)
        tempData.temperature in 10.0..35.0 -> Color(0,128,0)
        tempData.temperature in 36.0..45.0 -> Color(255,222,33)
        else -> Color.Red
    }

    val statusText = when {
        tempData.temperature < 10 -> stringResource(R.string.cold)
        tempData.temperature in 10.0..42.0 -> stringResource(R.string.normal)
        tempData.temperature in 43.0..45.0 -> stringResource(R.string.warning)
        else -> stringResource(R.string.hot)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.device_thermostat),
            contentDescription = null,
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(backgroundColor)
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = statusText,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_regular)),
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                fontWeight = MaterialTheme.typography.headlineMedium.fontWeight,
                lineHeight = MaterialTheme.typography.headlineMedium.lineHeight,
                letterSpacing = MaterialTheme.typography.headlineMedium.letterSpacing,
            )
        )

        Text(
            text = stringResource(R.string.device_temp_sensors_desc),
            style = MaterialTheme.typography.bodySmall.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_light)),
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                lineHeight = MaterialTheme.typography.bodySmall.lineHeight,
                letterSpacing = MaterialTheme.typography.bodySmall.letterSpacing,
            )
        )
    }
}