package com.example.allinone.auth.presentation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.allinone.R
import com.example.allinone.auth.data.remote.impl.AuthenticationManager
import com.example.allinone.auth.presentation.sealed.AuthResponse
import com.example.allinone.auth.presentation.sealed.RegistrationFormEvent
import com.example.allinone.auth.presentation.sealed.ValidationEvent
import com.example.allinone.auth.presentation.vm.SignInViewModel
import com.example.allinone.core.extension.toastMessage
import com.example.allinone.navigation.screen.AuthScreens
import com.example.allinone.navigation.screen.HomeScreens
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current

    val authenticationManager by remember {
        mutableStateOf(AuthenticationManager(context = context))
    }

    val signInViewModel : SignInViewModel = hiltViewModel()

    LaunchedEffect(key1 = context) {
        signInViewModel.validationEvents.collect { event ->
            when (event) {

                is ValidationEvent.Success -> Log.d("Success case","Successfully logged in.")

                is ValidationEvent.Error -> {
                    toastMessage(
                        context = context,
                        message = event.message
                    )
                }

                is ValidationEvent.NavigateToHome -> {
                    navController.navigate(HomeScreens.Home.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Spacer(modifier = Modifier.height(80.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .size(64.dp)
                        .background(Color(0xFFFFBB33)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.logo),
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.sign_in),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold)),
                        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                        fontWeight = MaterialTheme.typography.headlineLarge.fontWeight,
                        letterSpacing = MaterialTheme.typography.headlineLarge.letterSpacing,
                        lineHeight = MaterialTheme.typography.headlineLarge.lineHeight,
                        platformStyle = MaterialTheme.typography.headlineLarge.platformStyle,
                        textAlign = MaterialTheme.typography.headlineLarge.textAlign,
                        textDirection = MaterialTheme.typography.headlineLarge.textDirection,
                    ),
                )

                Spacer(modifier = Modifier.height(48.dp))

                SignInForm(
                    navController = navController,
                    authenticationManager = authenticationManager,
                    signInViewModel = signInViewModel
                )
            }
        }
    }
}

@Composable
private fun SignInForm(
    navController: NavHostController,
    authenticationManager: AuthenticationManager,
    signInViewModel: SignInViewModel
) {
    val context = LocalContext.current
    val state = signInViewModel.state
    var focusManager = LocalFocusManager.current
    var isPasswordVisible by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(24.dp)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // email & password fields
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.email),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.outline,
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_light))
                    )
                )

                ValidatedEmailTextField(
                    email = signInViewModel.email,
                    updateState = { input -> signInViewModel.updateEmail(input) },
                    validatorHasErrors = signInViewModel.emailHasErrors
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.password),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.outline,
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_light))
                    )
                )

                OutlinedTextField(
                    value = state.password,
                    onValueChange = {
                        signInViewModel.onEvent(
                            context = context,
                            RegistrationFormEvent.PasswordChanged(it)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .onFocusChanged { focus ->
                            if (focus.isFocused) {
                                state.passwordError == null
                            }
                        },
                    shape = RoundedCornerShape(16.dp),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                isPasswordVisible = !isPasswordVisible
                            }
                        ) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                            )
                        }
                    },
                    isError = state.passwordError != null,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            signInViewModel.onEvent(
                                context = context,
                                RegistrationFormEvent.Submit
                            )
                        }
                    )
                )
                if (state.passwordError != null) {
                    Text(
                        text = state.passwordError,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                TextButton(
                    onClick = {
                        navController.navigate(AuthScreens.ForgotPassword.route)
                    },
                ) {
                    Text(
                        text = stringResource(R.string.forgot_password),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = FontFamily(Font(R.font.inknut_antiqua_light))
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    signInViewModel.onEvent(
                        context = context,
                        RegistrationFormEvent.Submit
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            ) {
                if (signInViewModel.state.isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        text = stringResource(R.string.sign_in),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold)),
                            fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            letterSpacing = MaterialTheme.typography.bodyLarge.letterSpacing,
                            platformStyle = MaterialTheme.typography.bodyLarge.platformStyle,
                            textAlign = MaterialTheme.typography.bodyLarge.textAlign,
                            textDirection = MaterialTheme.typography.bodyLarge.textDirection,
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = {
                    navController.navigate(AuthScreens.SignUp.route)
                },
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(R.string.create_an_account),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_light))
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            SignInWithGoogle(
                authenticationManager = authenticationManager,
                navController = navController
            )
        }
    }
}

@Composable
private fun SignInWithGoogle(
    authenticationManager: AuthenticationManager,
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            scope.launch {
                authenticationManager.signInWithGoogle()
                    .catch { exception ->
                        toastMessage(
                            context = context,
                            message = exception.message ?: "Sign in failed!"
                        )
                    }
                    .collect { response ->
                        when (response) {
                            is AuthResponse.Success -> {
                                toastMessage(
                                    context = context,
                                    message = "Sign in successfully!"
                                )
                                navController.navigate(HomeScreens.Home.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                }
                            }
                            is AuthResponse.Error -> {
                                toastMessage(
                                    context = context,
                                    message = response.message
                                )
                            }

                            is AuthResponse.Loading -> {
                                toastMessage(
                                    context = context,
                                    message = "Loading..."
                                )
                            }
                        }
                    }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(R.drawable._123025_logo_google_g_icon),
                contentDescription = "Google Logo",
                modifier = Modifier
                    .align(Alignment.CenterStart)
            )
            Text(
                text = "Sign in with Google",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold)),
                    fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                    lineHeight = MaterialTheme.typography.bodyLarge.lineHeight,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    letterSpacing = MaterialTheme.typography.bodyLarge.letterSpacing,
                    platformStyle = MaterialTheme.typography.bodyLarge.platformStyle,
                    textAlign = MaterialTheme.typography.bodyLarge.textAlign,
                    textDirection = MaterialTheme.typography.bodyLarge.textDirection,
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun ValidatedEmailTextField(
    email: String,
    updateState: (String) -> Unit,
    validatorHasErrors: Boolean
) {
    var focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = email,
        onValueChange = updateState,
        isError = validatorHasErrors,
        supportingText = {
            if (validatorHasErrors) {
                Text(
                    text = "Incorrect email format."
                )
            }
        },
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        ),
    )
}