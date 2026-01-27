package com.example.presentation.art_space_app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
internal fun ArtInformationLayout(title: String, author: String, year: String) {

    Column(
        modifier = Modifier
            .padding(top = 40.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.primaryContainer),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Start,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(bottom = 8.dp, start = 16.dp, end = 16.dp, top = 16.dp)
        )
        Row {
            Text(
                text = author,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(bottom = 16.dp, start = 16.dp)
            )
            Text(
                text = year,
                fontWeight = FontWeight.Thin,
                modifier = Modifier.padding(bottom = 16.dp, start = 4.dp, end = 16.dp)
            )
        }
    }
}