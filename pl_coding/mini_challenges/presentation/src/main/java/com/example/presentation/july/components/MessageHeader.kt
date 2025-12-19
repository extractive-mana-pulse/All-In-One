package com.example.presentation.july.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.allinone.R

@Composable
fun MessageHeader(
    username: String,
    postCategory: String,
    verticalPadding: Dp,
    postCategoryBg: Color,
    postCategoryTextColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = verticalPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = username,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White,
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        letterSpacing = 0.1.sp,
                        fontWeight = FontWeight.W600

                    )
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = stringResource(R.string.main_post_subreddit),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.W500,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 0.1.sp,
                        color = Color(0xFFAFB2B9)
                    )
                )
            }

            Text(
                text = postCategory,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = postCategoryTextColor,
                    fontWeight = FontWeight.W500,
                    fontSize = 10.sp,
                    lineHeight = 12.sp,
                    letterSpacing = 0.1.sp,
                ),
                modifier = Modifier
                    .background(
                        postCategoryBg,
                        RoundedCornerShape(4.dp)
                    )
                    .padding(2.dp)
            )
        }
    }
}