package com.example.presentation.july.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.allinone.pl_coding.mini_challenges.presentation.R

@Composable
internal fun ReplyIconAndText() {
    Row(
        modifier = Modifier.padding(start = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            ImageVector.vectorResource(R.drawable.reply),
            contentDescription = null,
            tint = Color.White
        )

        Text(
            text = stringResource(R.string.reply),
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.White,
                fontSize = 15.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
            ),
            modifier = Modifier.padding(start = 8.dp, end = 16.dp),
        )
    }
}