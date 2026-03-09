package com.example.presentation.help.components

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

internal fun openGmail(
    context: Context,
    subject: String,
    body: String
) {
    val gmailIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:".toUri()
        putExtra(Intent.EXTRA_EMAIL, arrayOf("mukhammadaminsalokhiddinov@gmail.com"))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
        setPackage("com.google.android.gm")
    }
    val fallbackIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:".toUri()
        putExtra(Intent.EXTRA_EMAIL, arrayOf("mukhammadaminsalokhiddinov@gmail.com"))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }
    val intentToLaunch = if (gmailIntent.resolveActivity(context.packageManager) != null) {
        gmailIntent
    } else {
        Intent.createChooser(fallbackIntent, "Send email via…")
    }
    context.startActivity(intentToLaunch)
}