package com.example.presentation.editProfile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.allinone.core.presentation.R
import com.example.presentation.components.AppTopBar
import com.example.presentation.components.FetchStatusError
import com.example.presentation.components.Loading
import com.example.presentation.components.PrimaryButton
import com.example.presentation.editProfile.components.MessageCard
import com.example.presentation.editProfile.components.ValidatedEmailTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavHostController = rememberNavController(),
) {
    val focusManager = LocalFocusManager.current
    val editProfileViewModel: EditProfileViewModel = hiltViewModel()
    val uiState by editProfileViewModel.uiState.collectAsStateWithLifecycle()
    val profileResponse by editProfileViewModel.profileResponse.collectAsStateWithLifecycle()
    val isLoading = editProfileViewModel.isLoading
    val fetchStatus by editProfileViewModel.fetchUserDataStatus.collectAsStateWithLifecycle()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> 
        uri?.let { editProfileViewModel.updateProfilePicture(it) }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.edit_profile),
                onNavigationClick = { navController.navigateUp() },
                onActionClick = {
                    IconButton(
                        onClick = {
                            editProfileViewModel.deleteProfilePicture()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.outline_person_add_24),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        when(fetchStatus) {
            EditProfileViewModel.FetchStatus.Loading -> Loading()
            EditProfileViewModel.FetchStatus.Error -> FetchStatusError(editProfileViewModel)
            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                                .clickable { imagePickerLauncher.launch("image/*") },
                            contentAlignment = Alignment.Center
                        ) {
                            val imageUri = uiState.selectedImageUri ?: uiState.profilePictureUrl

                            if (imageUri != null) {
                                AsyncImage(
                                    model = imageUri,
                                    contentDescription = "Selected profile picture",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(
                                    painter = painterResource(R.drawable.outline_account_circle_24),
                                    contentDescription = null,
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .padding(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.outline_edit_24),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // full name and email
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.full_name),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.outline,
                                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_light))
                                )
                            )

                            OutlinedTextField(
                                value = uiState.displayName,
                                onValueChange = { editProfileViewModel.updateDisplayName(it) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    capitalization = KeyboardCapitalization.Words,
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = {
                                        focusManager.moveFocus(FocusDirection.Down)
                                    }
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = stringResource(R.string.email),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.outline,
                                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_light))
                                )
                            )

                            ValidatedEmailTextField(
                                email = uiState.email,
                                updateState = { input -> editProfileViewModel.updateEmail(input) },
                                validatorHasErrors = editProfileViewModel.emailHasErrors
                            )
                        }
                    }
                    
                    when (val response = profileResponse) {
                        is ProfileResponse.Error -> {
                            MessageCard(
                                message = response.message,
                                isError = true,
                                onDismiss = { editProfileViewModel.clearProfileResponse() }
                            )
                        }
                        is ProfileResponse.Success -> {
                            MessageCard(
                                message = "Profile updated successfully",
                                isError = false,
                                onDismiss = { editProfileViewModel.clearProfileResponse() }
                            )
                        }
                        else -> {}
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    PrimaryButton(
                        onClick = {
                            editProfileViewModel.saveChanges {
                                navController.navigateUp()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .align(Alignment.BottomCenter),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            Loading()
                        } else {
                            Text(
                                text = stringResource(R.string.save_changes),
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_bold)),
                                    fontWeight = MaterialTheme.typography.labelLarge.fontWeight,
                                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                    letterSpacing = MaterialTheme.typography.labelLarge.letterSpacing,
                                    lineHeight = MaterialTheme.typography.labelLarge.lineHeight,
                                    platformStyle = MaterialTheme.typography.labelLarge.platformStyle,
                                    textDirection = MaterialTheme.typography.labelLarge.textDirection,
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}