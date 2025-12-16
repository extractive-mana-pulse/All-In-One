package com.example.allinone.plcoding.mini_challenges.presentation.july

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.allinone.R
import com.example.allinone.main.presentation.screens.HomeScreen
import com.example.allinone.plcoding.mini_challenges.presentation.july.components.MessageHeader
import com.example.collapsiblechatthread.Comment1
import com.example.collapsiblechatthread.Comment2
import com.example.collapsiblechatthread.Comment3

@Composable
fun CollapsibleChatThreadRoot() {
    CollapsibleChatThreadScreen(
        modifier = Modifier
    )
}

@Preview
@Composable
fun CollapsibleChatThreadScreen(
    modifier: Modifier = Modifier
) {

    var expanded by remember { mutableStateOf(false) }

    var onReplyIsExpanded by remember { mutableStateOf(false) }
    var repliesHeight by remember { mutableStateOf(0.dp) }
    var messageContentHeight by remember { mutableStateOf(0.dp) }
    var messageContainerHeight by remember { mutableStateOf(0.dp) }

    var onComment2Height by remember { mutableStateOf(0.dp) }

    var onReplyIsExpandedComment3 by remember { mutableStateOf(false) }
    var repliesHeightComment3 by remember { mutableStateOf(0.dp) }
    var messageHeightComment3 by remember { mutableStateOf(0.dp) }

    Scaffold {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF06060A),
                            Color(0xFF0F1318),
                        )
                    )
                )
                .padding(it),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
            ) {

                AvatarAndVerticalLine(
                    usernameFirst = stringResource(R.string.main_post_username).first().toString(),
                    messageContentHeight = messageContentHeight,
                    expanded = expanded,
                    onCollapseClicked = {
                        expanded = false
                    },
                    usernameFirstBg = Color(0xFF75FABF),
                    repliesHeight = repliesHeight,
                    repliesIsExpanded = onReplyIsExpanded,
                    messageContainerHeight = messageContainerHeight,
                    comment2Height = onComment2Height,
                    repliesHeightComment3 = repliesHeightComment3,
                    onReplyIsExpandedComment3 = { onReplyIsExpandedComment3 },
                    onCollapsedStateChangedComment3 = {
                        onReplyIsExpandedComment3 = false
                    },
                    messageHeightComment3 = messageHeightComment3
                )

                MessageContent(
                    postTitle = stringResource(R.string.main_post_title),
                    postBody = stringResource(R.string.main_post_body),
                    showReplies = "Show 3 Replies",
                    onHeightCalculated = { height ->
                        messageContentHeight = height
                    },
                    onExpandClicked = {
                        expanded = true
                    },
                    expanded = expanded,
                    repliesHeight = {
                        repliesHeight = it
                    },
                    onReplyIStartExpanded = {
                        onReplyIsExpanded = it
                    },
                    onCollapseClicked = {
                        onReplyIsExpanded = it
                    },
                    messageContainerHeight = {
                        messageContainerHeight = it
                    },
                    onComment2HeightCalculated = {
                        onComment2Height = it
                    },
                    repliesHeightComment3 = {
                        repliesHeightComment3 = it
                    },
                    onReplyIsExpandedComment3 = {
                        onReplyIsExpandedComment3 = it
                    },
                    onCollapsedStateChangedComment3 = {
                        onReplyIsExpandedComment3 = it
                    },
                    messageHeightComment3 = {
                        messageHeightComment3 = it
                    }
                )
            }
        }
    }
}

@Composable
private fun MessageContent(
    postTitle: String,
    postBody: String,
    showReplies: String,
    onHeightCalculated: (Dp) -> Unit,
    onExpandClicked: () -> Unit,
    expanded: Boolean,
    repliesHeight: (Dp) -> Unit,
    onReplyIStartExpanded: (Boolean) -> Unit,
    onCollapseClicked: (Boolean) -> Unit,
    messageContainerHeight: (Dp) -> Unit,
    onComment2HeightCalculated: (Dp) -> Unit,
    repliesHeightComment3: (Dp) -> Unit,
    onReplyIsExpandedComment3: (Boolean) -> Unit,
    onCollapsedStateChangedComment3: (Boolean) -> Unit,
    messageHeightComment3: (Dp) -> Unit
) {
    val density = LocalDensity.current

    Column(
        modifier = Modifier
            .padding(end = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    val height = with(density) { coordinates.size.height.toDp() }
                    onHeightCalculated(height)
                }
        ) {
            MessageHeader(
                username = stringResource(R.string.main_post_username),
                postCategory = stringResource(R.string.main_post_category),
                verticalPadding = 8.dp,
                postCategoryBg = Color(0x801F222A),
                postCategoryTextColor = Color(0xFFAFB2B9)
            )

            Text(
                text = postTitle,
                style = MaterialTheme.typography.titleSmall.copy(
                    color = Color.White,
                    fontSize = 20.sp,
                    lineHeight = 26.sp,
                    letterSpacing = 1.sp
                ),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 4.dp)
            )

            Text(
                text = postBody,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFFAFB2B9),
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 1.sp,
                ),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 14.dp),
            )
            Row(
                modifier = Modifier.padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!expanded) {
                    Row(
                        modifier = Modifier.clickable {
                            onExpandClicked()
                        },
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

                Row(
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
                            letterSpacing = 1.sp,
                        ),
                        modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                    )
                }
            }
        }

        if (expanded) {
            ExpandedReplies(
                repliesHeight = { repliesHeight(it) },
                onReplyIsExpanded = {  onReplyIStartExpanded(it) },
                onCollapsedStateChanged = { onCollapseClicked(it) },
                messageHeight = {  messageContainerHeight(it) },
                comment2Height = { onComment2HeightCalculated(it) },
                repliesHeightComment3 = { repliesHeightComment3(it) },
                onReplyIsExpandedComment3 = { onReplyIsExpandedComment3(it) },
                onCollapsedStateChangedComment3 = { onCollapsedStateChangedComment3(it) },
                messageHeightComment3 = { messageHeightComment3(it) }
            )
        }
    }
}

@Composable
private fun AvatarAndVerticalLine(
    usernameFirst: String,
    messageContentHeight: Dp,
    expanded: Boolean,
    onCollapseClicked: () -> Unit = {},
    usernameFirstBg: Color,
    repliesHeight: Dp,
    repliesIsExpanded: Boolean = false,
    messageContainerHeight: Dp,
    comment2Height: Dp,
    repliesHeightComment3: Dp,
    onReplyIsExpandedComment3: (Boolean) -> Unit = {},
    onCollapsedStateChangedComment3: (Boolean) -> Unit = {},
    messageHeightComment3: Dp
) {
    val comment1FullHeightWithReplies = repliesHeight
    val heightOfTheMessageComment1 = messageContainerHeight

    Column(
        modifier = Modifier.padding(start = 16.dp)
    ) {
        // Avatar
        Text(
            text = usernameFirst,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFF0F0F18)
            ),
            modifier = Modifier
                .padding(top = 16.dp)
                .size(28.dp)
                .background(usernameFirstBg, CircleShape)
                .wrapContentSize(Alignment.Center)
        )

        // Main vertical line from avatar to message end
        if (!expanded) {
            // Simple case - just one vertical line
            Box(
                modifier = Modifier
                    .padding(start = 14.dp) // Center line under avatar
                    .width(2.dp)
                    .height(messageContentHeight - 50.dp)
                    .background(
                        DividerDefaults.color.copy(alpha = 0.3f)
                    )
            )

            // Horizontal line at the end
            Box(
                modifier = Modifier
                    .padding(start = 14.dp)
                    .width(24.dp)
                    .height(1.dp)
                    .background(
                        DividerDefaults.color.copy(alpha = 0.3f)
                    )
            )
        } else {
            Column(
                modifier = Modifier.padding(start = 14.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(messageContentHeight - 68.dp)
                        .background(
                            DividerDefaults.color.copy(alpha = 0.3f)
                        )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.offset(x = (-11).dp)
                ) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.minus_circle),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { onCollapseClicked() },
                        tint = Color.White
                    )
                }

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(48.dp)
                        .background(
                            DividerDefaults.color.copy(alpha = 0.3f)
                        )
                )

                // Horizontal line to first reply
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(1.dp)
                        .background(
                            DividerDefaults.color.copy(alpha = 0.3f)
                        )
                )

                // Animated vertical line for replies
                val animatedHeight by animateDpAsState(
                    targetValue = if(repliesIsExpanded) comment1FullHeightWithReplies else heightOfTheMessageComment1,
                )

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(animatedHeight + 24.dp)
                        .background(
                            DividerDefaults.color.copy(alpha = 0.3f)
                        )
                )

                if (!repliesIsExpanded) {
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(1.dp)
                            .background(
                                DividerDefaults.color.copy(alpha = 0.3f)
                            )
                    )
                }

                // this vertical line is from second comment to third.
                if (repliesIsExpanded) {
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(animatedHeight - comment2Height + 250.dp)
                            .background(
                                DividerDefaults.color.copy(alpha = 0.3f)
                            )
                    )
                    // this horizontal line is third comment.
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(1.dp)
                            .offset(
                                y = (-comment2Height) - 24.dp
                            )
                            .background(
                                DividerDefaults.color.copy(alpha = 0.3f)
                            )
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(comment2Height + 24.dp)
                            .background(
                                DividerDefaults.color.copy(alpha = 0.3f)
                            )
                    )
                }

                // this horizontal line is third comment.
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(1.dp)
                        .background(
                            DividerDefaults.color.copy(alpha = 0.3f)
                        )
                )
            }
        }
    }
}
/** This is the end of main post UI elements */

@Composable
fun ExpandedReplies(
    repliesHeight: (Dp) -> Unit,
    onReplyIsExpanded: (Boolean) -> Unit,
    onCollapsedStateChanged: (Boolean) -> Unit,
    messageHeight: (Dp) -> Unit,
    comment2Height: (Dp) -> Unit,
    repliesHeightComment3: (Dp) -> Unit,
    onReplyIsExpandedComment3: (Boolean) -> Unit,
    onCollapsedStateChangedComment3: (Boolean) -> Unit,
    messageHeightComment3: (Dp) -> Unit
) {
    var comment2ContainerHeight by remember { mutableStateOf(comment2Height) }

    var repliesHeightTemp by remember { mutableStateOf(repliesHeight) }
    var ownExpandState by remember { mutableStateOf(onReplyIsExpanded) }
    var messageContainerHeight by remember { mutableStateOf(messageHeight) }


    Log.d("TAG", "ExpandedReplies: $ownExpandState")

    Log.d("TAG", "ExpandedReplies: $repliesHeightTemp")

    Log.d("TAG", "ExpandedReplies: $messageContainerHeight")

    Log.d("TAG", "ExpandedReplies: $comment2ContainerHeight")

    Box(
        modifier = Modifier
            .padding(start = 16.dp, top = 35.dp)
    ) {
        Column(
            modifier = Modifier
        ) {
            // First reply
            Comment1(
                username = stringResource(R.string.comment1_username),
                usernameFirst = stringResource(R.string.comment1_username).first().toString(),
                usernameFirstBg = Color(0xFFFFC368),
                body = stringResource(R.string.comment1_body),
                showReplies = "Show 4 Replies",
                heightOfBox = 0.dp,
                onHeightChanged = { repliesHeight(it) },
                onReplyIsExpanded = { onReplyIsExpanded(it) },
                onCollapsedStateChanged = { onCollapsedStateChanged(it) },
                heightOfTheMessage = { messageHeight(it) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Comment2(
                username = stringResource(R.string.comment2_username),
                usernameFirst = stringResource(R.string.comment2_username).first().toString(),
                usernameFirstBg = Color(0xFF75FABF),
                body = stringResource(R.string.comment2_body),
                heightOfComment2 = {
                    comment2Height(it)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Comment3(
                username = stringResource(R.string.comment3_username),
                usernameFirst = stringResource(R.string.comment3_username).first().toString(),
                usernameFirstBg = Color(0xFFFFC368),
                body = stringResource(R.string.comment3_body),
                showReplies = "Show 2 Replies",
                heightOfTheBox = 0.dp,
                onHeightChanged = { repliesHeightComment3(it) },
                onReplyIsExpanded = { onReplyIsExpandedComment3(it) },
                onCollapsedStateChanged = { onCollapsedStateChangedComment3(it) },
                heightOfTheMessage = { messageHeightComment3(it) },
            )
        }
    }
}