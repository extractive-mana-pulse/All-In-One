package com.example.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.allinone.core.presentation.R
import com.example.domain.OnBoardModel

@Composable
internal fun OnBoardItem(page: OnBoardModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = page.imageRes),
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 20.dp)
                .clip(CircleShape),

        )
        if (page.id == 1) {
            TypewriterTextEffect(
                text = stringResource(page.title),
                maxCharacterChunk = 1,
                maxDelayInMillis = 550
            ) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold))
                    )
                )
            }
        } else {
            Text(
                text = stringResource(page.title),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold))
                )
            )
        }
        Spacer(
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Text(
            text = stringResource(page.description),
            style = MaterialTheme.typography.labelMedium.copy(
                fontFamily = FontFamily(Font(R.font.inknut_antiqua_light)),
                lineHeight = 20.sp
            )
        )
        Spacer(
            modifier = Modifier.padding(bottom = 56.dp)
        )
    }
}