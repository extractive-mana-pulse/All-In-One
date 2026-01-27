package com.example.presentation.july.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.allinone.pl_coding.mini_challenges.presentation.R

@Composable
fun Comment1(
    username: String,
    usernameFirst: String,
    usernameFirstBg: Color,
    body:String,
    showReplies: String,
    // this is height of the box that contains all content.
    heightOfBox: Dp,
    // this onHeightChanged lambda method is when comment 1 fully expanded with all replies.
    onHeightChanged: (Dp) -> Unit = {},
    onReplyIsExpanded: (Boolean) -> Unit = {},
    onCollapsedStateChanged: (Boolean) -> Unit = {},
    heightOfTheMessage: (Dp) -> Unit
) {
    val minHeight = 30.dp
    val density = LocalDensity.current

    var comment1Height by remember { mutableStateOf(0.dp) }
    var comment1reply1Height by remember { mutableStateOf(0.dp) }
    var comment1reply2Height by remember { mutableStateOf(0.dp) }
    var comment1reply3Height by remember { mutableStateOf(0.dp) }
    var comment1reply4Height by remember { mutableStateOf(0.dp) }
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var heightOfBoxOfReplies by remember { mutableStateOf(heightOfBox) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
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

                if (!isExpanded) {
                    VerticalDivider(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .height(comment1Height - minHeight - 20.dp), // 20.dp is height of header and his paddings.
                        color = DividerDefaults.color.copy(0.3f, 255f, 255f, 255f)
                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .width(24.dp)
                            .offset(x = 11.dp),
                        color = DividerDefaults.color.copy(0.3f, 255f, 255f, 255f)
                    )
                } else {
                    // When expanded - show vertical line to collapse button
                    VerticalDivider(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .height(comment1reply1Height - minHeight - 38.dp),
                        color = DividerDefaults.color.copy(0.3f, 255f, 255f, 255f)
                    )

                    // Collapse button
                    Icon(
                        ImageVector.vectorResource(R.drawable.minus_circle),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            isExpanded = false
                            onCollapsedStateChanged(false)
                        }
                    )

                    // Vertical line from collapse button to first reply
                    VerticalDivider(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .height(minHeight), // Space to reach first reply
                        color = DividerDefaults.color.copy(0.3f, 255f, 255f, 255f)
                    )
                    // Reply 1 - Horizontal arrow + Vertical line
                    HorizontalDivider(
                        modifier = Modifier
                            .width(24.dp)
                            .offset(x = 12.dp),
                        color = DividerDefaults.color.copy(0.3f, 255f, 255f, 255f)
                    )
                    VerticalDivider(
                        modifier = Modifier.height(comment1reply1Height + 8.dp),
                        color = DividerDefaults.color.copy(0.3f, 255f, 255f, 255f)
                    )

                    // Reply 2 - Horizontal arrow + Vertical line
                    HorizontalDivider(
                        modifier = Modifier
                            .width(24.dp)
                            .offset(x = 12.dp),
                        color = DividerDefaults.color.copy(0.3f, 255f, 255f, 255f)
                    )
                    VerticalDivider(
                        modifier = Modifier.height(comment1reply2Height + 4.dp),
                        color = DividerDefaults.color.copy(0.3f, 255f, 255f, 255f)
                    )

                    // Reply 3 - Horizontal arrow + Vertical line
                    HorizontalDivider(
                        modifier = Modifier
                            .width(24.dp)
                            .offset(x = 12.dp),
                        color = DividerDefaults.color.copy(0.3f, 255f, 255f, 255f)
                    )
                    VerticalDivider(
                        modifier = Modifier.height(comment1reply3Height + 16.dp),
                        color = DividerDefaults.color.copy(0.3f, 255f, 255f, 255f)
                    )

                    // Reply 4 - Horizontal arrow only (last reply, no vertical line after)
                    HorizontalDivider(
                        modifier = Modifier
                            .width(24.dp)
                            .offset(x = 12.dp),
                        color = DividerDefaults.color.copy(0.3f, 255f, 255f, 255f)
                    )

                }

            }
            /** Avatar and vertical line ends here */

            Column(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        val height = with(density) { coordinates.size.height.toDp() }
                        comment1Height = height
                        heightOfTheMessage(comment1Height)
                    }
            ) {
                MessageHeader(
                    username = username,
                    postCategory = stringResource(R.string.comment1_badge),
                    verticalPadding = 0.dp,
                    postCategoryBg = Color(0x1FFFC368),
                    postCategoryTextColor = Color(0xFFFFC368)
                )

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

                Row(
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                                    fontSize = 15.sp,
                                    lineHeight = 20.sp,
                                    letterSpacing = 1.sp,
                                ),
                                modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                            )
                        }
                    }
                    ReplyIconAndText()
                }
                // Usage - Simplified replies animation
                /** Comment1 replies here - only show when expanded */
                AnimatedVisibility(
                    visible = isExpanded,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically(),
                    modifier = Modifier.padding(start = 8.dp).onGloballyPositioned { coordinates ->
                        val heightOfWholeReplies = with(density) { coordinates.size.height.toDp() }
                        heightOfBoxOfReplies = heightOfWholeReplies
                        onHeightChanged(heightOfWholeReplies)
                    }
                ) {
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Reply 1
                        ReplyItemContent(
                            modifier = Modifier.padding(top = 10.dp),
                            username = R.string.comment1_reply1_username,
                            body = R.string.comment1_reply1_body,
                            onHeightMeasured = { height -> comment1reply1Height = height },
                            avatarBackground = Color(0xFF68C3FF),
                            bottomPadding = 10.dp
                        )

                        // Reply 2
                        ReplyItemContent(
                            username = R.string.comment1_reply2_username,
                            body = R.string.comment1_reply2_body,
                            onHeightMeasured = { height -> comment1reply2Height = height },
                            avatarBackground = Color(0xFF75FABF),
                            bottomPadding = 10.dp
                        )

                        // Reply 3
                        ReplyItemContent(
                            username = R.string.comment1_reply3_username,
                            body = R.string.comment1_reply3_body,
                            onHeightMeasured = { height -> comment1reply3Height = height },
                            avatarBackground = Color(0xFFFFC368),
                            bottomPadding = 10.dp
                        )

                        // Reply 4
                        ReplyItemContent(
                            username = R.string.comment1_reply4_username,
                            body = R.string.comment1_reply4_body,
                            onHeightMeasured = { height -> comment1reply4Height = height },
                            avatarBackground = Color(0xFF68C3FF),
                            bottomPadding = 10.dp
                        )
                    }
                }
            }
        }
    }
}