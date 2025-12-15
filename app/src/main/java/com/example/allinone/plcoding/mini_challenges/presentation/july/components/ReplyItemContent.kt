package com.example.collapsiblechatthread

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.allinone.R


// Updated ReplyItemContent - removed offsetY parameter
@Composable
fun ReplyItemContent(
    modifier: Modifier = Modifier,
    username: Int,
    body: Int,
    avatarBackground: Color,
    onHeightMeasured: (Dp) -> Unit,
    bottomPadding: Dp
) {
    val density = LocalDensity.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = bottomPadding),
        verticalAlignment = Alignment.Top
    ) {
        // Avatar
        Text(
            text = stringResource(username).first().toString(),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.W700,
                textAlign = TextAlign.Center,
                lineHeight = 1.sp,
                color = Color(0xFF0F0F18)
            ),
            modifier = Modifier
                .size(28.dp)
                .background(
                    color = avatarBackground,
                    shape = CircleShape
                )
                .wrapContentSize(Alignment.Center)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Content
        Column(
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    val height = with(density) { coordinates.size.height.toDp() }
                    onHeightMeasured(height)
                }
        ) {
            // Username and subreddit
            Row(
                modifier = Modifier.padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(username),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 15.sp,
                        lineHeight = 20.sp,
                        letterSpacing = 0.1.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier.padding(end = 4.dp)
                )

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

            // Body text
            Text(
                text = stringResource(body),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFFAFB2B9),
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 0.1.sp,
                ),
                modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Reply icons
            ReplyIconAndText()
        }
    }
}