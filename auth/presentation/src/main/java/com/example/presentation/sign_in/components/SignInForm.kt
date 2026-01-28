package com.example.presentation.sign_in.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.allinone.core.presentation.R
import com.example.presentation.ValidatedEmailTextField
import com.example.presentation.ValidatedPasswordTextField
import com.example.presentation.components.PrimaryButton
import com.example.presentation.sign_in.SignInViewModel
import com.example.presentation.sign_up.RegistrationFormEvent

@Composable
fun SignInForm(
    onSignInWithGoogle: () -> Unit,
    viewModel: SignInViewModel,
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignInWithGoogleButton(onClick = onSignInWithGoogle,)

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
            Text(
                text = "OR",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Email TextField
        ValidatedEmailTextField(
            email = viewModel.email,
            updateState = { viewModel.updateEmail(it) },
            validatorHasErrors = viewModel.emailHasErrors,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password TextField
        ValidatedPasswordTextField(
            password = viewModel.state.collectAsState().value.password,
            updateState = { viewModel.onEvent(RegistrationFormEvent.PasswordChanged(it)) },
            validatorHasErrors = viewModel.state.collectAsState().value.passwordError != null
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Forgot Password
        Text(
            text = "Forgot Password?",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.End)
                .clickable(enabled = true) {
                    onNavigateToForgotPassword()
                }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Sign In Button
        PrimaryButton(
            onClick = {
                viewModel.onEvent(RegistrationFormEvent.Submit)
            },
            enabled = !viewModel.state.collectAsState().value.isLoading,
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = stringResource(R.string.sign_in),
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Sign Up Link
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account? ",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(enabled = true) {
                    onNavigateToSignUp()
                }
            )
        }
    }
}