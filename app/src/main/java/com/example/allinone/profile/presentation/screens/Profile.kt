package com.example.allinone.profile.presentation.screens

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.allinone.R
import com.example.allinone.auth.data.remote.impl.AuthenticationManager
import com.example.allinone.auth.domain.model.UserCredentials
import com.example.allinone.core.extension.toastMessage
import com.example.allinone.core.util.ui.Loading
import com.example.allinone.navigation.screen.AuthScreens
import com.example.allinone.navigation.screen.ProfileScreens
import com.example.allinone.profile.presentation.vm.EditProfileViewModel
import com.example.allinone.profile.presentation.vm.EditProfileViewModelFactory
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController = rememberNavController(),
    userCredentials: UserCredentials?
) {

    val context = LocalContext.current
    val authenticationManager = AuthenticationManager(context)
    val tabTitles = listOf(
        "Blogs",
        "Bookmarks"
    )
    val selectedTabIndex = remember { mutableIntStateOf(0) }

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.empty_page)
    )
    var isPlaying by remember { mutableStateOf(true) }

    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = isPlaying
    )

    val authManager = AuthenticationManager(context)

    val editProfileViewModel: EditProfileViewModel = viewModel(
        factory = EditProfileViewModelFactory(authManager)
    )

    val fetchStatus by editProfileViewModel.fetchUserDataStatus.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {

                    IconButton(
                        onClick = {
                            // create a logic with SignIn.route.
                            // i need to implement if possible a state as a string or no meter. so when user press sign out button
                            // user have to sign out depending on state from authentication manager.
                            // if sign out was successful, then use navController.
                            authenticationManager.signOut()
                            navController.navigate(AuthScreens.SignIn.route)
                        }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Default.Logout,
                            contentDescription = null
                        )
                    }

                    IconButton(
                        onClick = {
                            navController.navigate(ProfileScreens.EditProfile.route)
                        }
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
    { paddingValues ->
        when(fetchStatus) {

            EditProfileViewModel.FetchStatus.Loading -> {
                Loading()
            }

            EditProfileViewModel.FetchStatus.Error -> {
                FetchStatusError(editProfileViewModel)
            }

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
                            model = Firebase.auth.currentUser?.photoUrl,
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

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Name: ${userCredentials?.displayName}",
                            fontSize = MaterialTheme.typography.headlineSmall.fontSize
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Email: ${userCredentials?.email}",
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
                            0 -> {
                                BlogsTab(composition, progress)
                            }
                            1 -> {
                                BookmarksTab(composition, progress)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BookmarksTab(
    composition: LottieComposition?,
    progress: Float
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .size(282.dp)
        )
        Text(
            text = "Your bookmarks page is currently empty.",
        )
        TextButton(
            onClick = {
                // navController.navigate(ProfileScreens.PublishBlog.route)
                toastMessage(
                    context = context,
                    message = "this function is maintenance."
                )
            }
        ) {
            Text(
                text = "Do you want to bookmark a new blog?"
            )
        }
    }
}

@Composable
private fun BlogsTab(
    composition: LottieComposition?,
    progress: Float
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .size(282.dp)
        )
        Text(
            text = "Your blogs page is currently empty.",
        )
        TextButton(
            onClick = {
                // navController.navigate(ProfileScreens.PublishBlog.route)
                toastMessage(
                    context = context,
                    message = "this function is maintenance."
                )
            }
        ) {
            Text(
                text = "Do you want to publish a new blog?"
            )
        }
    }
}