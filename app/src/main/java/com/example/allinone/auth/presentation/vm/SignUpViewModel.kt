package com.example.allinone.auth.presentation.vm

import android.content.Context
import android.util.Log
import androidx.compose.runtime.derivedStateOf
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
import com.example.allinone.auth.presentation.sealed.SignUpEvent
import com.example.allinone.auth.presentation.sealed.SignUpEvent.ClearPasswordError
import com.example.allinone.auth.presentation.sealed.SignUpEvent.PasswordChanged
import com.example.allinone.auth.presentation.sealed.SignUpEvent.Submit
import com.example.allinone.auth.presentation.sealed.ValidationEvent
import com.example.allinone.core.extension.toastMessage
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val authenticationManager: AuthenticationManager,
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
) : ViewModel() {

    var state by mutableStateOf(RegistrationFormState())
        private set

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    var email by mutableStateOf("")
        private set

    val emailHasErrors by derivedStateOf {
        if (email.isNotEmpty()) {
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else {
            false
        }
    }

    fun updateEmail(input: String) {
        email = input
    }

    fun onSignUpEvents(
        context: Context,
        event: SignUpEvent
    ) {
        when(event) {

            ClearPasswordError -> {
                state = state.copy(password = "")
            }

            is PasswordChanged -> {
                state = state.copy(password = event.password)
            }

            Submit -> {
                submitData(context)
            }
        }
    }

    fun performSignUp(context: Context) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            authenticationManager.createAccountWithEmailAndPassword(
                email = email,
                password = state.password
            ).collectLatest { response ->
                state = state.copy(isLoading = false)

                when (response) {
                    is AuthResponse.Success -> {
                        validationEventChannel.send(ValidationEvent.NavigateToHome)
                    }
                    is AuthResponse.Error -> {
                        toastMessage(
                            context = context,
                            message = "Error: ${response.message}"
                        )
                        Log.d("Error", "Error: ${response.message}")
                    }
                    is AuthResponse.Loading -> {
                        Log.d("SignUp", "Loading")
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

        if(hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
            )
            return
        }

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
            performSignUp(context = context)
        }
    }
}