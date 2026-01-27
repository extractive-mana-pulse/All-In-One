package com.example.presentation.sign_up

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
import com.example.presentation.SignUpWithEmailUseCase
import com.example.presentation.sealed.SignUpEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpWithEmail: SignUpWithEmailUseCase,
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
) : ViewModel() {

    var state by mutableStateOf(RegistrationFormState())
        private set

    var email by mutableStateOf("")
        private set

    private val _events = Channel<ValidationEvent>()
    val events = _events.receiveAsFlow()

    val emailHasErrors by derivedStateOf {
        email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun updateEmail(input: String) {
        email = input
    }

    fun onEvent(event: SignUpEvent) {
        when (event) {
            SignUpEvent.ClearPasswordError ->
                state = state.copy(passwordError = null)

            is SignUpEvent.PasswordChanged ->
                state = state.copy(password = event.password)

            SignUpEvent.Submit ->
                submit()
        }
    }

    private fun submit() {
        val emailResult = validateEmail.execute(email)
        val passwordResult = validatePassword.execute(state.password)

        val hasError = listOf(emailResult, passwordResult)
            .any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            return
        }
        signUp()
    }
    private fun signUp() {
        viewModelScope.launch {
            signUpWithEmail(email, state.password).collectLatest { result ->
                when (result) {
                    is AuthResult.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is AuthResult.Success -> {
                        state = state.copy(isLoading = false)
                        _events.send(ValidationEvent.NavigateToHome)
                    }

                    is AuthResult.Error -> {
                        state = state.copy(isLoading = false)
                        _events.send(
                            ValidationEvent.Error(result.message)
                        )
                    }
                }
            }
        }
    }
}