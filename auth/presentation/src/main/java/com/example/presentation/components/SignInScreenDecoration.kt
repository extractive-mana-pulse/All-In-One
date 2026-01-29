package com.example.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun SignInScreenDecoration(isSignUp: Boolean = false) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Top left leaf-like shape
        Box(
            modifier = Modifier
                .size(150.dp)
                .offset(x = (-40).dp, y = (-20).dp)
                .background(Color(0xFF2D6A4F).copy(alpha = 0.3f), CircleShape)
        )
        
        if (!isSignUp) {
            // Plant illustration placeholder
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 20.dp, y = 40.dp)
            )
        }
    }
}