package com.example.presentation.july.components

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
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.allinone.pl_coding.mini_challenges.presentation.R

@Composable
fun Comment2(
    username: String,
    usernameFirst: String,
    usernameFirstBg: Color,
    body:String,
    heightOfComment2: (Dp) -> Unit
) {
    val density = LocalDensity.current
    var comment2Height by remember { mutableStateOf(0.dp) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        // Avatar and vertical line
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = usernameFirst,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF0F0F18)
                ),
                modifier = Modifier
                    .size(28.dp)
                    .background(usernameFirstBg, CircleShape)
                    .wrapContentSize(Alignment.Center)
            )

            Spacer(modifier = Modifier.height(8.dp))

            VerticalDivider(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .height(comment2Height - 50.dp),
                color = DividerDefaults.color.copy(0.3f, 255f, 255f, 255f)
            )
            HorizontalDivider(
                modifier = Modifier
                    .width(24.dp)
                    .offset(x = 11.dp),
                color = DividerDefaults.color.copy(0.3f, 255f, 255f, 255f)
            )
        }

        Column(
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    val height = with(density) { coordinates.size.height.toDp() }
                    comment2Height = height
                    heightOfComment2(comment2Height)
                }
        ) {
            MessageHeader(
                username = username,
                postCategory = stringResource(R.string.comment2_badge),
                verticalPadding = 0.dp,
                postCategoryBg = Color(0x1F68C3FF),
                postCategoryTextColor = Color(0xFF68C3FF)
            )

            // this is the message body.
            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFFAFB2B9),
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 0.1.sp,
                ),
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )

            RepliesLayoutWithIconAndText()
        }
    }
}

@Composable
private fun RepliesLayoutWithIconAndText() {
    Row(
        modifier = Modifier.padding(start = 8.dp, top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .padding(start = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                ImageVector.vectorResource(R.drawable.plus_circle),
                contentDescription = null,
                tint = Color.White
            )
            Text(
                text = "Show 3 Replies",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.White,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 1.sp,
                ),
                modifier = Modifier.padding(start = 8.dp, end = 16.dp),
            )
        }
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
                text = "Reply",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.White,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 1.sp,
                ),
                modifier = Modifier.padding(start = 8.dp, end = 16.dp),
            )
        }
    }
}