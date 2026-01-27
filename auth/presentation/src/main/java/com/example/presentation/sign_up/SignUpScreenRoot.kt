package com.example.presentation.sign_up

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.allinone.core.presentation.R
import com.example.presentation.components.PrimaryButton
import com.example.presentation.components.ValidatedEmailTextField
import com.example.presentation.sealed.SignUpEvent
import com.example.presentation.toastMessage

@Preview(showBackground = true)
@Composable
fun SignUpScreenRoot(
    onNavigateToSignIn: () -> Unit = {},
) {
    val context = LocalContext.current
    val signUpViewModel: SignUpViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        signUpViewModel.events.collect { event ->
            when (event) {
                is ValidationEvent.NavigateToHome -> onNavigateToSignIn
                is ValidationEvent.Error -> {
                    toastMessage(
                        context = context,
                        message = event.message
                    )
                }
                is ValidationEvent.Success -> onNavigateToSignIn()
            }
        }
    }
    SignUpScreen(signUpViewModel = signUpViewModel)
}
@Composable
private fun SignUpScreen(
    signUpViewModel: SignUpViewModel
) {
    val state = signUpViewModel.state
    val focusManager = LocalFocusManager.current
    var isPasswordVisible by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(80.dp))

                Text(
                    text = stringResource(R.string.sign_up),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(40.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF5F5F5)
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {

                            Text(
                                text = stringResource(R.string.email),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            ValidatedEmailTextField(
                                email = signUpViewModel.email,
                                updateState = signUpViewModel::updateEmail,
                                validatorHasErrors = signUpViewModel.emailHasErrors
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = stringResource(R.string.password),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            OutlinedTextField(
                                value = state.password,
                                onValueChange = {
                                    signUpViewModel.onEvent(
                                        SignUpEvent.PasswordChanged(it)
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp),
                                shape = RoundedCornerShape(16.dp),
                                visualTransformation =
                                    if (isPasswordVisible) VisualTransformation.None
                                    else PasswordVisualTransformation(),
                                singleLine = true,
                                trailingIcon = {
                                    IconButton(onClick = {
                                        isPasswordVisible = !isPasswordVisible
                                    }) {
                                        Icon(
                                            painter = painterResource(
                                                if (isPasswordVisible)
                                                    R.drawable.outline_visibility_24
                                                else
                                                    R.drawable.visibility_off_24
                                            ),
                                            contentDescription = null
                                        )
                                    }
                                },
                                isError = state.passwordError != null,
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        focusManager.clearFocus()
                                        signUpViewModel.onEvent(SignUpEvent.Submit)
                                    }
                                )
                            )

                            if (state.passwordError != null) {
                                Text(
                                    text = state.passwordError ?: "Unknown error occurred, please try again!",
                                    color = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        PrimaryButton(
                            onClick = {
                                signUpViewModel.onEvent(SignUpEvent.Submit)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .padding(horizontal = 16.dp),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            if (state.isLoading) {
                                CircularProgressIndicator()
                            } else {
                                Text(text = stringResource(R.string.sign_up))
                            }
                        }
                    }
                }
            }
        }
    }
}
