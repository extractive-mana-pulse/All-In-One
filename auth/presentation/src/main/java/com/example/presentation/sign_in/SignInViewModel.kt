package com.example.presentation.sign_in

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinone.core.presentation.R
import com.example.domain.model.SignInResult
import com.example.domain.repository.GoogleAuthUiClientRepo
import com.example.presentation.sign_up.RegistrationFormEvent
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val googleAuthUiClient: GoogleAuthUiClientRepo
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    // ---------------- EMAIL ----------------
    fun updateEmail(email: String) {
        _state.update {
            it.copy(
                email = email,
                emailError = validateEmail(email)
            )
        }
    }

    val email: String
        get() = _state.value.email

    val emailHasErrors: Boolean
        get() = _state.value.emailError != null

    // ---------------- EVENTS ----------------
    fun onEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.PasswordChanged -> {
                _state.update {
                    it.copy(
                        password = event.password,
                        passwordError = validatePassword(event.password)
                    )
                }
            }

            RegistrationFormEvent.Submit -> {
                submitEmailPasswordSignIn()
            }

            else -> Unit
        }
    }

    // ---------------- EMAIL/PASSWORD SIGN IN ----------------
    private fun submitEmailPasswordSignIn() {
        val emailError = validateEmail(_state.value.email)
        val passwordError = validatePassword(_state.value.password)

        _state.update {
            it.copy(
                emailError = emailError,
                passwordError = passwordError
            )
        }

        if (emailError != null || passwordError != null) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            // TODO: connect to Firebase email/password here
            // Fake success for now
            kotlinx.coroutines.delay(1000)

            _state.update {
                it.copy(
                    isLoading = false,
                    isSignInSuccessful = true
                )
            }
        }
    }

    // ---------------- GOOGLE SIGN IN (your existing code) ----------------
    fun signIn(context: Context) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val result = try {
                val credentialManager = CredentialManager.create(context)

                val googleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .setAutoSelectEnabled(false)
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val credentialResponse = credentialManager.getCredential(
                    request = request,
                    context = context
                )

                when (val credential = credentialResponse.credential) {
                    is CustomCredential -> {
                        if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                            val googleIdTokenCredential =
                                GoogleIdTokenCredential.createFrom(credential.data)

                            val firebaseResult =
                                googleAuthUiClient.signInWithCredential(googleIdTokenCredential.idToken)

                            SignInResult(
                                data = firebaseResult.data,
                                errorMessage = firebaseResult.errorMessage
                            )
                        } else {
                            SignInResult(null, "Unexpected credential type")
                        }
                    }

                    else -> SignInResult(null, "Unexpected credential")
                }
            } catch (e: Exception) {
                SignInResult(null, e.message)
            }

            _state.update { it.copy(isLoading = false) }
            onSignInResult(result)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            googleAuthUiClient.signOut()
            resetState()
        }
    }

    private fun onSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage,
                userData = result.data
            )
        }
    }

    fun resetState() {
        _state.update { SignInState() }
    }

    // ---------------- VALIDATION ----------------
    private fun validateEmail(email: String): String? {
        if (email.isBlank()) return "Email can't be empty"
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return "Invalid email"
        return null
    }

    private fun validatePassword(password: String): String? {
        if (password.length < 6) return "Password must be at least 6 chars"
        return null
    }
}