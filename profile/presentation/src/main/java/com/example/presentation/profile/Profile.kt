package com.example.presentation.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.allinone.core.presentation.R
import com.example.domain.model.UserData
import com.example.presentation.components.AppTopBar
import com.example.presentation.components.CustomAlertDialog
import com.example.presentation.components.FetchStatusError
import com.example.presentation.components.Loading
import com.example.presentation.editProfile.EditProfileViewModel
import com.example.presentation.profile.components.BlogsTab
import com.example.presentation.profile.components.BookmarksTab

@Composable
fun ProfileScreenRoot() {

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onNavigateToEditProfile: () -> Unit = {},
    onNavigateToSignOut:() -> Unit = {},
    onNavigateUp: () -> Unit = {},
    editProfileViewModel: EditProfileViewModel = hiltViewModel(),
    userData: UserData?,
) {
    val tabTitles = listOf(
        "Blogs",
        "Bookmarks"
    )
    val selectedTabIndex = remember { mutableIntStateOf(0) }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty_page))
    var isPlaying by remember { mutableStateOf(true) }
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = isPlaying
    )
    val openAlertDialog = remember { mutableStateOf(false) }
    val fetchStatus by editProfileViewModel.fetchUserDataStatus.collectAsStateWithLifecycle()

    Scaffold(
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
                        onClick = {
                            onNavigateToEditProfile()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.outline_edit_24),
                            contentDescription = null
                        )
                    }
                }
            )
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
//                    authenticationManager.signOut()
                    onNavigateToSignOut()
                },
                icon = painterResource(R.drawable.outline_logout_24),
                confirmText = stringResource(R.string.logout),
                dismissText = stringResource(R.string.dismiss)
            )
        }

        when(fetchStatus) {

            EditProfileViewModel.FetchStatus.Loading -> Loading()

            EditProfileViewModel.FetchStatus.Error -> FetchStatusError(editProfileViewModel)

            else ->  {
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
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                ),
                            contentScale = ContentScale.Crop
                        )
//                        if (userData?.profilePictureUrl != null) {
//                            AsyncImage(
//                                model = "https://picsum.photos/200",
//                                contentDescription = null,
//                                modifier = Modifier
//                                    .size(108.dp)
//                                    .clip(CircleShape)
//                                    .border(
//                                        width = 2.dp,
//                                        color = MaterialTheme.colorScheme.primary,
//                                        shape = CircleShape
//                                    ),
//                                contentScale = ContentScale.Crop
//                            )
//                        } else {
//                            Icon(
//                                painter = painterResource(R.drawable.outline_account_circle_24),
//                                contentDescription = null,
//                                modifier = Modifier.size(108.dp),
//                            )
//                        }

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
            }
        }
    }
}