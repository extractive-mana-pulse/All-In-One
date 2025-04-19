package com.example.allinone.auth.presentation.vm

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allinone.auth.data.remote.impl.AuthenticationManager
import com.example.allinone.auth.domain.model.RegistrationFormState
import com.example.allinone.auth.domain.use_case.ValidateEmail
import com.example.allinone.auth.domain.use_case.ValidatePassword
import com.example.allinone.auth.presentation.sealed.AuthResponse
import com.example.allinone.auth.presentation.sealed.RegistrationFormEvent
import com.example.allinone.auth.presentation.sealed.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authenticationManager: AuthenticationManager,
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
): ViewModel() {
    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegistrationFormEvent) {
        when(event) {
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegistrationFormEvent.CreateAccount -> {
                // navigate to create an account screen
            }
            is RegistrationFormEvent.ForgotPassword -> {
                // navigate to forgot password screen
            }
            is RegistrationFormEvent.SignInWithGoogle -> {
                // navigate to sign in with google screen
            }
            is RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }

    fun performSignIn() {
        viewModelScope.launch {
            authenticationManager.signInWithEmailAndPassword(
                email = state.email,
                password = state.password
            ).collectLatest { response ->
                when (response) {
                    is AuthResponse.Success -> {
                        validationEventChannel.send(ValidationEvent.NavigateToHome)
                    }

                    is AuthResponse.Error -> {
                        Log.e("TAG", "sigIn: ${response.message}")
                    }

                    is AuthResponse.Loading -> {}
                }
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any { !it.successful }

        if(hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
            )
            return
        }

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
            performSignIn()
        }
    }
}