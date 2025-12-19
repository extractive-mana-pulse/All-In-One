package com.example.collapsiblechatthread

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.allinone.R
import com.example.allinone.plcoding.mini_challenges.presentation.july.components.MessageHeader

@Composable
fun Comment3(
    username: String,
    usernameFirst: String,
    usernameFirstBg: Color,
    body:String,
    showReplies: String,
    heightOfTheBox: Dp,
    onHeightChanged: (Dp) -> Unit = {},
    onReplyIsExpanded: (Boolean) -> Unit = {},
    onCollapsedStateChanged: (Boolean) -> Unit = {},
    heightOfTheMessage: (Dp) -> Unit
) {
    val minHeight = 30.dp
    val density = LocalDensity.current
    var comment1Height by remember { mutableStateOf(0.dp) }

    var isExpanded by rememberSaveable { mutableStateOf(false) }

    var comment3reply1 by remember { mutableStateOf(0.dp) }
    var comment3reply2 by remember { mutableStateOf(0.dp) }

    var heightOfBoxOfReplies by remember { mutableStateOf(heightOfTheBox) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        /** Avatar and vertical line */
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

            // Draw lines based on expanded state
            if (!isExpanded) {
                VerticalDivider(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .height(comment1Height - 50.dp),
                    color = DividerDefaults.color.copy(0.3f, 255f, 255f, 255f)
                )
                HorizontalDivider(
                    modifier = Modifier
                        .width(24.dp)
                        .offset(x = 11.dp),
                    color = DividerDefaults.color.copy(0.3f, 255f, 255f, 255f)
                )
            } else {
                // this is first vertical line.
                VerticalDivider(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .height(comment1Height - minHeight - 330.dp),
                    color = DividerDefaults.color.copy(0.3f, 255f, 255f, 255f)
                )

                // this is an icon to collapse expanded replies of comment.
                Icon(
                    ImageVector.vectorResource(R.drawable.minus_circle),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        isExpanded = false
                        onCollapsedStateChanged(false)
                    }
                )

                // Additional vertical lines for replies
                VerticalDivider(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .height(minHeight - 16.dp),
                    color = DividerDefaults.color.copy(0.3f, 255f, 255f, 255f)
                )

                // Repeat the pattern for each reply
                repeat(2) { index ->
                    HorizontalDivider(
                        modifier = Modifier
                            .width(24.dp)
                            .offset(x = 12.dp),
                        color = DividerDefaults.color.copy(0.3f, 255f, 255f, 255f)
                    )

                    VerticalDivider(
                        modifier = Modifier.height(
                            when(index) {
                                0 -> comment3reply1 + minHeight + 8.dp
                                else -> {
                                    0.dp
                                }
                            }
                        ),
                        color = DividerDefaults.color.copy(0.3f, 255f, 255f, 255f)
                    )
                }
            }
        }
        /** Avatar and vertical line ends here */
        // TODO: ALL DONE HERE
        Column(
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    val height = with(density) { coordinates.size.height.toDp() }
                    comment1Height = height
                    heightOfTheMessage(comment1Height)
                }
        ) {

            // TODO: ALL DONE HERE
            MessageHeader(
                username = username,
                postCategory = stringResource(R.string.comment1_badge),
                verticalPadding = 0.dp,
                postCategoryBg = Color(0x1FFFC368),
                postCategoryTextColor = Color(0xFFFFC368)
            )

            // TODO: ALL DONE HERE
            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFFAFB2B9),
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 0.1.sp,
                    fontWeight = FontWeight.W400
                ),
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )

            // TODO: ALL DONE HERE
            Row(
                modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // TODO: ALL DONE HERE
                if (!isExpanded) {

                    Row(
                        modifier = Modifier
                            .clickable {
                                isExpanded = true // Expand
                                onReplyIsExpanded(true)
                            }
                            .padding(start = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            ImageVector.vectorResource(R.drawable.plus_circle),
                            contentDescription = null,
                            tint = Color.White
                        )
                        Text(
                            text = showReplies,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.W500,
                                fontSize = 15.sp,
                                lineHeight = 20.sp,
                                letterSpacing = 0.1.sp,
                            ),
                            modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                        )
                    }
                }

                // TODO: ALL DONE HERE
                ReplyIconAndText()
            }

            /** Comment1 replies here - only show when expanded */
            // TODO: ALL DONE HERE
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier.onGloballyPositioned { coordinates ->
                        val heightOfWholeReplies = with(density) { coordinates.size.height.toDp() }
                        heightOfBoxOfReplies = heightOfWholeReplies
                        onHeightChanged(heightOfWholeReplies)
                    }
                ) {

                    // Reply 1
                    ReplyItemContent(
                        username = R.string.comment3_reply1_username,
                        body = R.string.comment3_reply1_body,
                        avatarBackground = Color(0xFF68C3FF),
                        onHeightMeasured = { height -> comment3reply1 = height },
                        bottomPadding = 10.dp

                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Reply 2
                    ReplyItemContent(
                        username = R.string.comment3_reply2_username,
                        body = R.string.comment3_reply2_body,
                        avatarBackground = Color(0xFF75FABF),
                        onHeightMeasured = { height -> comment3reply2 = height },
                        bottomPadding = 10.dp

                    )
                }
            }
        }
    }
}