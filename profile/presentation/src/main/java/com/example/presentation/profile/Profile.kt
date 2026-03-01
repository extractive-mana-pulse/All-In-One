@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.allinone.core.presentation.R
import com.example.domain.model.UserData
import com.example.presentation.components.AllInOneIconButton
import com.example.presentation.components.AllInOneTextButton
import com.example.presentation.components.CustomAlertDialog
import com.example.presentation.profile.components.BlogsTab
import com.example.presentation.profile.components.BookmarksTab

@Composable
fun ProfileScreen(
    onNavigateToEditProfile: () -> Unit = {},
    onNotificationClicked: () -> Unit = {},
    onNavigateToSignOut: () -> Unit = {},
    onShareProfileClick: () -> Unit = {},
    onSearchClicked: () -> Unit = {},
    onNavigateUp: () -> Unit = {},
    userData: UserData?,
) {
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty_page))
    var isPlaying by remember { mutableStateOf(true) }
    val progress by animateLottieCompositionAsState(composition, isPlaying = isPlaying)
    val openAlertDialog = remember { mutableStateOf(false) }

    Scaffold(
        // Disable Scaffold's built-in inset handling so we control insets manually
        contentWindowInsets = WindowInsets(0),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTopBar(
                title = "",
                onNavigationClick = {
                    onNavigateUp()
                },
                onActionClick = {
                    IconButton(
                        onClick = {
                            openAlertDialog.value = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.outline_logout_24),
                            contentDescription = null
                        )
                    }
                    IconButton(
                        onClick = onNavigateToEditProfile
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.outline_edit_24),
                            contentDescription = null
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = userData?.username ?: "Profile",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.weight(1f)
                )
                AllInOneIconButton(
                    icon = painterResource(R.drawable.outline_search_24),
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(surfaceCard)
                        .align(Alignment.CenterVertically),
                    onClick = onSearchClicked,
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(16.dp))
                AllInOneIconButton(
                    icon = painterResource(R.drawable.outline_notifications_24),
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(surfaceCard)
                        .align(Alignment.CenterVertically),
                    onClick = onNotificationClicked,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )
    { paddingValues ->
        if (openAlertDialog.value) {
            CustomAlertDialog(
                dialogTitle = stringResource(R.string.logout),
                dialogText = stringResource(R.string.logout_desc),
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    openAlertDialog.value = false
                    onNavigateToSignOut()
                },
                icon = painterResource(R.drawable.outline_logout_24),
                confirmText = stringResource(R.string.logout),
                dismissText = stringResource(R.string.dismiss)
            )
        }
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                AsyncImage(
                    model = userData?.profilePictureUrl ?: run {
                        val initials = userData?.username
                            ?.split(" ")
                            ?.mapNotNull { it.firstOrNull() }
                            ?.take(2)
                            ?.joinToString("")
                            ?: "U"
                        "https://ui-avatars.com/api/?name=$initials&size=200&background=4285f4&color=fff&bold=true"
                    },
                    contentDescription = null,
                    modifier = Modifier
                        .size(108.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        ),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Name: ${userData?.username}",
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Email: ${userData?.email}",
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                )

                Spacer(modifier = Modifier.height(16.dp))

                TabRow(
                    selectedTabIndex.intValue,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex.intValue == index,
                            onClick = { selectedTabIndex.intValue = index },
                            text = { Text(title) }
                        )
                    }
                }
                when (selectedTabIndex.intValue) {
                    0 -> BlogsTab(composition, progress)
                    1 -> BookmarksTab(composition, progress)
                }
            }
        }

        // Avatar sits on top of the card, centered, offset upward
        AsyncImage(
            model = userData?.profilePictureUrl ?: run {
                val initials = userData?.username
                    ?.split(" ")
                    ?.mapNotNull { it.firstOrNull() }
                    ?.take(2)
                    ?.joinToString("")
                    ?: "U"
                "https://ui-avatars.com/api/?name=$initials&size=200&background=4285f4&color=fff&bold=true"
            },
            contentDescription = null,
            modifier = Modifier
                .size(96.dp)
                .align(Alignment.TopCenter)
                .offset(y = (-48).dp)
                .clip(CircleShape)
                .border(3.dp, MaterialTheme.colorScheme.background, CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun StatItem(
    text: String,
    label: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f)
            )
        )
    }
}