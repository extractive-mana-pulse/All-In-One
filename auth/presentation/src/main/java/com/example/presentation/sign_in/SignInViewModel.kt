package com.example.presentation.sign_in

import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.ValidateEmail
import com.example.domain.model.AuthResult
import com.example.domain.model.RegistrationFormState
import com.example.domain.use_case.ValidatePassword
import com.example.presentation.SignInWithEmailUseCase
import com.example.presentation.SignInWithGoogleUseCase
import com.example.presentation.sign_up.RegistrationFormEvent
import com.example.presentation.sign_up.ValidationEvent
import com.example.presentation.toastMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
) : ViewModel() {

    var state by mutableStateOf(RegistrationFormState())
        private set

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    var email by mutableStateOf("")
        private set

    val emailHasErrors by derivedStateOf {
        if (email.isNotEmpty()) {
            !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            false
        }
    }

    fun updateEmail(input: String) {
        email = input
        // Clear email error when user starts typing
        if (state.emailError != null) {
            state = state.copy(emailError = null)
        }
    }

    fun onEvent(
        context: Context,
        event: RegistrationFormEvent
    ) {
        when (event) {
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
                // Clear password error when user starts typing
                if (state.passwordError != null) {
                    state = state.copy(passwordError = null)
                }
            }

            is RegistrationFormEvent.ClearPasswordError -> {
                state = state.copy(passwordError = null)
            }

            is RegistrationFormEvent.CreateAccount -> {
                // Navigation handled in UI layer
            }

            is RegistrationFormEvent.ForgotPassword -> {
                // Navigation handled in UI layer
            }
            is RegistrationFormEvent.SignInWithGoogle -> signInWithGoogle(context)

            is RegistrationFormEvent.Submit -> submitData(context = context)
        }
    }

    private fun signInWithGoogle(context: Context) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            signInWithGoogleUseCase().collectLatest { response ->
                state = state.copy(isLoading = false)

                when (response) {
                    is AuthResult.Success -> {
                        toastMessage(
                            context = context,
                            message = "Sign in successfully!"
                        )
                        validationEventChannel.send(ValidationEvent.NavigateToHome)
                    }

                    is AuthResult.Error -> {
                        toastMessage(
                            context = context,
                            message = response.message
                        )
                    }

                    is AuthResult.Loading -> {
                        Log.d(TAG, "Google Sign In - Loading")
                    }
                }
            }
        }
    }

    private fun performSignIn(context: Context) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            signInWithEmailUseCase(
                email = email,
                password = state.password
            ).collectLatest { response ->
                state = state.copy(isLoading = false)

                when (response) {
                    is AuthResult.Success -> {
                        validationEventChannel.send(ValidationEvent.NavigateToHome)
                    }

                    is AuthResult.Error -> {
                        toastMessage(
                            context = context,
                            message = "Error: ${response.message}"
                        )
                    }

                    is AuthResult.Loading -> {
                        Log.d(TAG, "Sign In - Loading")
                    }
                }
            }
        }
    }

    private fun submitData(context: Context) {
        val emailResult = validateEmail.execute(email)
        val passwordResult = validatePassword.execute(state.password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
            )
            return
        }

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
            performSignIn(context = context)
        }
    }

    companion object {
        private const val TAG = "SignInViewModel"
    }
}