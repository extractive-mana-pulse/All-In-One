package com.example.presentation.business_card_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.allinone.codelabs.presentation.R
import com.example.presentation.business_card_app.components.CredentialsItem

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BusinessCard(
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD8E8D8))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(R.drawable.android_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .background(Color(0xFF073042))
                        .size(140.dp)
                )
                Text(
                    modifier = Modifier,
                    text = "Jennifer Doe",
                    style = MaterialTheme.typography.displayLarge
                )
                Text(
                    text = "Android developer extraordinaire",
                    color = Color(0xFF3A7A3A),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth(1f)
                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            // item 1
            CredentialsItem(
                icon = painterResource(com.example.allinone.core.presentation.R.drawable.outline_contact_phone_24),
                content = "+ 11 (123) 444 555 66"
            )
            // item 2
            CredentialsItem(
                icon = painterResource(com.example.allinone.core.presentation.R.drawable.outline_share_24),
                content = "@AndroidDev"
            )
            // item 3
            CredentialsItem(
                icon = painterResource(com.example.allinone.core.presentation.R.drawable.outline_mail_24),
                content = "jen.doe@android.com"
            )
        }
    }
}