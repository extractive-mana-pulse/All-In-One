package com.example.presentation.sign_up

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.data.R
import com.example.presentation.sealed.ValidationEvent
import com.example.presentation.sign_up.components.SignUpForm
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

                SignUpForm(signUpViewModel)
            }
        }
    }
}