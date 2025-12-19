package com.example.presentation.vm

import android.net.Uri
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinone.auth.data.remote.impl.AuthenticationManager
import com.example.allinone.auth.presentation.sealed.AuthResponse
import com.example.allinone.profile.domain.model.EditProfileUiState
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

// TODO Fix error with full name and email.
// validation form should be re-build.

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val authManager: AuthenticationManager,
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()
    
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    private val _successMessage = MutableStateFlow<String?>(null)
    val successMessage: StateFlow<String?> = _successMessage.asStateFlow()

    private val _fetchUserDataStatus = MutableStateFlow<FetchStatus>(FetchStatus.Initial)
    val fetchUserDataStatus = _fetchUserDataStatus
        .onStart {
            fetchUserData()
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = FetchStatus.Initial
        )

    enum class FetchStatus {
        Initial,
        Loading,
        Success,
        Error
    }

    fun fetchUserData() {
        viewModelScope.launch {
            try {
                _fetchUserDataStatus.value = FetchStatus.Loading
                _loading.value = true

                authManager.fetchUserData().collectLatest { user ->
                    user?.let {
                        _uiState.value = EditProfileUiState(
                            displayName = it.displayName,
                            profilePictureUrl = it.imageUrl,
                            email = it.email
                        )
                        _fetchUserDataStatus.value = FetchStatus.Success
                    } ?: run {
                        _fetchUserDataStatus.value = FetchStatus.Error
                    }
                    _loading.value = false
                }
            } catch (e: Exception) {
                _fetchUserDataStatus.value = FetchStatus.Error
                _loading.value = false
                _errorMessage.value = "Failed to fetch user data: ${e.message}"
            }
        }
    }

    fun updateDisplayName(newDisplayName: String) {
        _uiState.value = _uiState.value.copy(displayName = newDisplayName)
    }

    val emailHasErrors by derivedStateOf {
        if (_uiState.value.email.isNotEmpty()) {
            !android.util.Patterns.EMAIL_ADDRESS.matcher(_uiState.value.email).matches()
        } else {
            false
        }
    }

    fun updateEmail(input: String) {
        _uiState.value = _uiState.value.copy(email = input)
    }

    fun updateProfilePicture(uri: Uri) {
        _uiState.value = _uiState.value.copy(selectedImageUri = uri)
    }

    fun deleteProfilePicture() {
        viewModelScope.launch {
            _loading.value = true

            try {
                val userId = FirebaseAuth.getInstance().currentUser?.uid

                if (userId != null) {

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
                            if (task.isSuccessful) {
                                _successMessage.value = "Profile picture deleted"
                            } else {
                                _errorMessage.value = "Failed to update profile: ${task.exception?.message}"
                            }
                            _loading.value = false
                        }
                } else {
                    _errorMessage.value = "User not logged in"
                    _loading.value = false
                }
            } catch (e: Exception) {
                _errorMessage.value = "An unexpected error occurred: ${e.message}"
                _loading.value = false
            }
        }
    }

    fun saveChanges(onSuccessNavigateBack: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            _errorMessage.value = null

            try {
                val imageUri = _uiState.value.selectedImageUri
                if (imageUri != null) {
                    val imageResult = authManager.uploadProfilePicture(imageUri).first()
                    when(imageResult) {
                        is AuthResponse.Error -> {
                            _errorMessage.value = "Failed to upload image: ${imageResult.message}"
                            _loading.value = false
                            return@launch
                        }
                        AuthResponse.Success -> {}
                        is AuthResponse.Loading -> {}
                    }
                }

                val displayName = _uiState.value.displayName
                if (displayName.isNotBlank()) {
                    val displayNameResult = authManager.updateDisplayNameProfile(displayName = displayName).first()
                    when(displayNameResult) {
                        is AuthResponse.Error -> {
                            _errorMessage.value = "Failed to update display name: ${displayNameResult.message}"
                            _loading.value = false
                            return@launch
                        }
                        AuthResponse.Success -> {}
                        is AuthResponse.Loading -> {}
                    }
                }
                _successMessage.value = "Profile updated successfully"
                onSuccessNavigateBack()

            } catch (e: Exception) {
                _errorMessage.value = "An unexpected error occurred: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
    fun clearMessages() {
        _errorMessage.value = null
        _successMessage.value = null
    }
}