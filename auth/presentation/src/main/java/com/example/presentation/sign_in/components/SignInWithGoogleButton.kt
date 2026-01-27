package com.example.presentation.sign_in.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.allinone.core.presentation.R
import com.example.presentation.components.PrimaryButton
import com.example.presentation.sign_in.SignInViewModel
import com.example.presentation.sign_up.RegistrationFormEvent

@Composable
internal fun SignInWithGoogleButton(
    viewModel: SignInViewModel
) {
    val context = LocalContext.current

    PrimaryButton(
        onClick = {
            viewModel.onEvent(
                context = context,
                RegistrationFormEvent.SignInWithGoogle
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        enabled = !viewModel.state.isLoading
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable._123025_logo_google_g_icon),
                contentDescription = "Google Logo",
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Text(
                text = "Sign in with Google",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily(Font(R.font.inknut_antiqua_semi_bold))
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}