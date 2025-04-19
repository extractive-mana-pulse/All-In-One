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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
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
import androidx.compose.ui.tooling.preview.Preview
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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

// TODO(MUST HAVE TO IMPLEMENT THIS. IT'S IN FIRST PLACE RIGHT NOW)
// need to finish code, optimize code, also don't forget to implement a navigation inside view model.
// it's important to have navigation inside view model cause it's decrease number of code,
// it improve code quality. no-more one time events.
// also modify code in SignInForm.
// after finishing implementation of all of these listed requirements above.
// check if everything works fine, if(yes) implement save structure to registration(create an account) page.


@OptIn(ExperimentalMaterial3Api::class)
@Preview
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
    val state = signInViewModel.state
    val context = LocalContext.current
    var focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
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
                    text = "Email",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.outline,
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_light))
                    )
                )

                OutlinedTextField(
                    value = state.email,
                    onValueChange = {
                        signInViewModel.onEvent(RegistrationFormEvent.EmailChanged(it))
                    },
                    isError = state.emailError != null,
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Email
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    )
                )
                if (state.emailError != null) {
                    Text(
                        text = state.emailError,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.End)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Password",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.outline,
                        fontFamily = FontFamily(Font(R.font.inknut_antiqua_light))
                    )
                )

                OutlinedTextField(
                    value = state.password,
                    onValueChange = {
                        signInViewModel.onEvent(RegistrationFormEvent.PasswordChanged(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    isError = state.passwordError != null,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            signInViewModel.onEvent(RegistrationFormEvent.Submit)
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
                        text = "Forgot Password?",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = FontFamily(Font(R.font.inknut_antiqua_light))
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    signInViewModel.onEvent(RegistrationFormEvent.Submit)
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
                Text(
                    text = "Sign in",
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
                )
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
                    text = "Create an account",
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
            authenticationManager.signInWithGoogle()
                .onEach { response ->
                    if (response is AuthResponse.Success) {
                        toastMessage(
                            context = context,
                            message = "Sign in successfully!"
                        )
                        navController.navigate(HomeScreens.Home.route)
                    }
                }
                .launchIn(scope)
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