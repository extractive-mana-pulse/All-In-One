package com.example.presentation.editProfile

import android.net.Uri
import android.util.Patterns
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.AuthResult
import com.example.domain.repository.AuthRepository
import com.example.presentation.editProfile.ProfileResponse.Error
import com.example.presentation.editProfile.ProfileResponse.Loading
import com.example.presentation.editProfile.ProfileResponse.Success
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProfileResponse {
    data object Loading : ProfileResponse
    data object Success : ProfileResponse
    data class Error(val message: String) : ProfileResponse
}

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val authManager: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    private val _profileResponse = MutableStateFlow<ProfileResponse?>(null)
    val profileResponse: StateFlow<ProfileResponse?> = _profileResponse.asStateFlow()

    private val _fetchUserDataStatus = MutableStateFlow<FetchStatus>(FetchStatus.Initial)
    val fetchUserDataStatus = _fetchUserDataStatus
        .onStart { fetchUserData() }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = FetchStatus.Initial
        )

    val emailHasErrors by derivedStateOf {
        _uiState.value.email.isNotEmpty() &&
                !Patterns.EMAIL_ADDRESS.matcher(_uiState.value.email).matches()
    }

    val isLoading: Boolean
        get() = _profileResponse.value is ProfileResponse.Loading

    enum class FetchStatus {
        Initial,
        Loading,
        Success,
        Error
    }

    fun fetchUserData() {
        viewModelScope.launch {
            _fetchUserDataStatus.value = FetchStatus.Loading
            _profileResponse.value = ProfileResponse.Loading

            try {
                authManager.fetchUserData().collectLatest { user ->
                    if (user != null) {
                        _uiState.value = EditProfileUiState(
                            displayName = user.displayName,
                            profilePictureUrl = user.imageUrl,
                            email = user.email
                        )
                        _fetchUserDataStatus.value = FetchStatus.Success
                        _profileResponse.value = null
                    } else {
                        _fetchUserDataStatus.value = FetchStatus.Error
                        _profileResponse.value = ProfileResponse.Error("User data not found")
                    }
                }
            } catch (e: Exception) {
                _fetchUserDataStatus.value = FetchStatus.Error
                _profileResponse.value = ProfileResponse.Error(
                    e.message ?: "Failed to fetch user data"
                )
            }
        }
    }

    fun updateDisplayName(newDisplayName: String) {
        _uiState.value = _uiState.value.copy(displayName = newDisplayName)
    }

    fun updateEmail(input: String) {
        _uiState.value = _uiState.value.copy(email = input)
    }

    fun updateProfilePicture(uri: Uri) {
        _uiState.value = _uiState.value.copy(selectedImageUri = uri)
    }

    fun deleteProfilePicture() {
        viewModelScope.launch {
            _profileResponse.value = ProfileResponse.Loading

            try {
                val userId = FirebaseAuth.getInstance().currentUser?.uid
                    ?: run {
                        _profileResponse.value = ProfileResponse.Error("User not logged in")
                        return@launch
                    }

                authManager.deleteImageFromFirebaseStorage(userId)

                _uiState.value = _uiState.value.copy(
                    selectedImageUri = null,
                    profilePictureUrl = null
                )

                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setPhotoUri(null)
                    .build()

                FirebaseAuth.getInstance().currentUser?.updateProfile(profileUpdates)
                    ?.addOnCompleteListener { task ->
                        _profileResponse.value = if (task.isSuccessful) {
                            ProfileResponse.Success
                        } else {
                            ProfileResponse.Error(
                                task.exception?.message ?: "Failed to delete profile picture"
                            )
                        }
                    }
            } catch (e: Exception) {
                _profileResponse.value = ProfileResponse.Error(
                    e.message ?: "An unexpected error occurred"
                )
            }
        }
    }

    fun saveChanges(onSuccessNavigateBack: () -> Unit) {
        viewModelScope.launch {
            _profileResponse.value = Loading

            try {
                // Upload profile picture if selected
                val imageUri = _uiState.value.selectedImageUri
                if (imageUri != null) {
                    when (val imageResult = authManager.uploadProfilePicture(imageUri.toString()).first()) {
                        is AuthResult.Error -> {
                            _profileResponse.value = Error(
                                "Failed to upload image: ${imageResult.message}"
                            )
                            return@launch
                        }
                        is AuthResult.Loading -> Unit
                        AuthResult.Success -> Unit
                    }
                }

                // Update display name if changed
                val displayName = _uiState.value.displayName
                if (displayName.isNotBlank()) {
                    when (val displayNameResult = authManager.updateDisplayNameProfile(displayName).first()) {
                        is AuthResult.Error -> {
                            _profileResponse.value = Error(
                                "Failed to update display name: ${displayNameResult.message}"
                            )
                            return@launch
                        }
                        AuthResult.Success -> {
                            _profileResponse.value = Success
                            onSuccessNavigateBack()
                        }
                        is AuthResult.Loading -> {}
                    }
                }

            } catch (e: Exception) {
                _profileResponse.value = Error(
                    e.message ?: "An unexpected error occurred"
                )
            }
        }
    }

    fun clearProfileResponse() {
        _profileResponse.value = null
    }
}