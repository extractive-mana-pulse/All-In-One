package com.example.presentation.sign_in

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

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authenticationManager: AuthenticationManager,
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
): ViewModel() {

    var state by mutableStateOf(RegistrationFormState())

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

    fun onEvent(
        context: Context,
        event: RegistrationFormEvent
    ) {
        when(event) {
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegistrationFormEvent.ClearPasswordError -> {
                state = state.copy(passwordError = "")
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
                submitData(context = context)
            }
        }
    }

    fun performSignIn(context: Context) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            authenticationManager.signInWithEmailAndPassword(
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
                    }
                    is AuthResponse.Loading -> { Log.d("SignIn", "Loading") }
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
            performSignIn(context = context)
        }
    }
}