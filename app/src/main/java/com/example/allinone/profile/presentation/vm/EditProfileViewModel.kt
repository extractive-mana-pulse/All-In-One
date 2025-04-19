package com.example.allinone.profile.presentation.vm

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.allinone.auth.data.remote.impl.AuthenticationManager
import com.example.allinone.auth.presentation.sealed.AuthResponse
import com.example.allinone.profile.domain.model.EditProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val authManager: AuthenticationManager
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

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun updateProfilePicture(uri: Uri) {
        _uiState.value = _uiState.value.copy(selectedImageUri = uri)
    }

    fun saveChanges(onSuccessNavigateBack: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            _errorMessage.value = null

            try {
                val imageUri = _uiState.value.selectedImageUri
                if (imageUri != null) {
                    val imageResult = authManager.uploadProfilePicture(imageUri).first()
                    when(imageResult){
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
                    val displayNameResult = authManager.updateUserProfile(displayName = displayName).first()
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

class EditProfileViewModelFactory(private val authManager: AuthenticationManager) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            return EditProfileViewModel(authManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
