package com.example.presentation.swipe.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.view.HapticFeedbackConstantsCompat
import androidx.core.view.ViewCompat
import com.example.allinone.core.presentation.R
import com.example.domain.model.SwipeGestureAction
import com.example.presentation.swipe.SwipeActionAppearance
import kotlinx.coroutines.launch

@Composable
fun ChatListSwipeGestureSelector(
    selectedAction: SwipeGestureAction,
    onActionSelected: (SwipeGestureAction) -> Unit,
    actionAppearance: (SwipeGestureAction) -> SwipeActionAppearance,
    modifier: Modifier = Modifier
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        SectionCard(title = "Post list swipe gesture", modifier = modifier) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MockSwipeChatRow(
                    modifier = Modifier.weight(1f),
                    appearance = actionAppearance(selectedAction),
                )
                SwipeActionPicker(
                    selectedAction = selectedAction,
                    onActionSelected = onActionSelected,
                    modifier = Modifier
                        .width(130.dp)
                        .height(90.dp)
                )
            }
        }
        Text(
            text = "Choose which action you want to perform when you swipe to the left in the chat list.",
            fontSize = 12.sp,
            color = Color(0xFF8A8A8A),
            lineHeight = 16.sp
        )
    }
}


@Composable
private fun MockSwipeChatRow(
    appearance: SwipeActionAppearance,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(start = 18.dp)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = 10.dp,
                        bottomStart = 10.dp,
                        topEnd = 16.dp,
                        bottomEnd = 16.dp
                    )
                )
                .border(
                    1.dp,
                    Color(0xFFE0E0E0),
                    RoundedCornerShape(
                        topStart = 10.dp,
                        bottomStart = 10.dp,
                        topEnd = 16.dp,
                        bottomEnd = 16.dp
                    )
                )
                .background(Color.White)
                .zIndex(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .offset(x = (-18).dp)
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFD0D0D0))
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier
                    .weight(1f)
                    .offset(x = (-18).dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFD0D0D0))
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(7.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFE8E8E8))
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(36.dp)
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(appearance.backgroundColor)
                    .zIndex(0f)
            ) {
                Icon(
                    painter = painterResource(appearance.iconRes),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}

/** Due to still at the moment when i've written this code no support of nested lazy column. i've decided to come up with ai approach to resolve the problem */
@Composable
private fun SwipeActionPicker(
    selectedAction: SwipeGestureAction,
    onActionSelected: (SwipeGestureAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val actions = SwipeGestureAction.entries
    val itemHeightDp = 30.dp
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = actions.indexOf(selectedAction).coerceAtLeast(0)
    )
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    val centreIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val view = LocalView.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(centreIndex) {
        if (centreIndex in actions.indices) {
            ViewCompat.performHapticFeedback(view, HapticFeedbackConstantsCompat.SEGMENT_TICK)
            onActionSelected(actions[centreIndex])
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        HorizontalDivider(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = itemHeightDp),
            thickness = 1.5.dp,
            color = Color(0xFF2AABEE)
        )
        HorizontalDivider(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = itemHeightDp * 2),
            thickness = 1.5.dp,
            color = Color(0xFF2AABEE)
        )

        LazyColumn(
            state = listState,
            flingBehavior = snapBehavior,
            contentPadding = PaddingValues(vertical = itemHeightDp),
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeightDp * 3)           // show exactly 3 slots = 90dp, in case you want to add more items just increase the number
                .pointerInput(Unit) {
                    awaitEachGesture {
                        val down = awaitFirstDown(requireUnconsumed = false)
                        down.consume()
                        do {
                            val event = awaitPointerEvent()
                            val drag = event.changes.firstOrNull() ?: break
                            val dragAmount = drag.positionChange().y
                            drag.consume()
                            coroutineScope.launch {
                                listState.scrollBy(-dragAmount)
                            }
                        } while (event.changes.any { it.pressed })
                    }
                }
        ) {
            itemsIndexed(actions) { index, action ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeightDp)
                ) {
                    Text(
                        text = action.name.lowercase(),
                        fontSize = if (index == centreIndex) 16.sp else 14.sp,
                        fontWeight = if (index == centreIndex) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (index == centreIndex) Color.LightGray else Color.Black
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0F2F5)
@Composable
private fun Preview() {
    var swipeAction by remember { mutableStateOf(SwipeGestureAction.DISABLE) }
    Box(modifier = Modifier.padding(16.dp)) {
        ChatListSwipeGestureSelector(
            selectedAction = swipeAction,
            onActionSelected = { swipeAction = it },
            actionAppearance = { action ->
                when (action) {
                    SwipeGestureAction.DELETE -> SwipeActionAppearance(
                        R.drawable.outline_folder_24,
                        Color(0xFFFF3B30)
                    )
                    SwipeGestureAction.DISABLE -> SwipeActionAppearance(
                        R.drawable.disabled_ic,
                        Color(0xFFBDBDBD)
                    )
                    SwipeGestureAction.PIN -> SwipeActionAppearance(
                        R.drawable.outline_settings_24,
                        Color(0xFF34C759)
                    )
                }
            }
        )
    }
}