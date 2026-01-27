package com.example.presentation.sign_up

import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

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
            !Patterns.EMAIL_ADDRESS.matcher(email).matches()
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