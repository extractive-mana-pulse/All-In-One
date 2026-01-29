package com.example.presentation.sign_up

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.allinone.core.presentation.R
import com.example.presentation.components.AuthTextField
import com.example.presentation.components.SignInScreenDecoration
import com.example.presentation.toastMessage

@Preview(showBackground = true)
@Composable
fun SignUpScreenRoot(
    modifier :Modifier = Modifier,
    onNavigateToSignIn: () -> Unit = {},
) {
    val context = LocalContext.current
    val signUpViewModel: SignUpViewModel = viewModel()

    LaunchedEffect(Unit) {
        signUpViewModel.events.collect { event ->
            when (event) {
                is ValidationAction.NavigateToHome -> onNavigateToSignIn()
                is ValidationAction.ShowError -> {
                    toastMessage(
                        context = context,
                        message = event.message ?: ""
                    )
                }
                is ValidationAction.Success -> onNavigateToSignIn()
                else -> Unit
            }
        }
    }
    SignUpScreen(
        onBackToLogin = onNavigateToSignIn,
        onSignUpClick = onNavigateToSignIn
    )
}
@Composable
fun SignUpScreen(
    onBackToLogin: () -> Unit = {},
    onSignUpClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF5DBB9B))
    ) {
        SignInScreenDecoration(isSignUp = true)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onBackToLogin() },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_arrow_back_24),
                            contentDescription = null,
                            tint = Color(0xFF1B4332),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Back to login",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B4332)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Sign Up",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = Color(0xFF1B4332),
                        ),
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 24.dp)
                    )

                    AuthTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = "Email",
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_mail_24),
                                contentDescription = null,
                                tint = Color.LightGray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    AuthTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = "Password",
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.lock_24px),
                                tint = Color.LightGray,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {},
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.visibility_off_24),
                                    contentDescription = null,
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    AuthTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = "Confirm Password",
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.lock_24px),
                                tint = Color.LightGray,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        trailingIcon = {
                            IconButton(
                                onClick = {},
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.visibility_off_24),
                                    contentDescription = null,
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(
                        onClick = onSignUpClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1B4332)
                        )
                    ) {
                        Text(text = "Sign Up", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
fun SignUpPreview(modifier: Modifier = Modifier) {
    SignUpScreenRoot(
        modifier = modifier,
        onNavigateToSignIn = {}
    )
}