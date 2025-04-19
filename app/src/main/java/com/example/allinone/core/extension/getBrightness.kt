package com.example.allinone.core.extension

import android.content.Context
import android.provider.Settings

fun getBrightness(context: Context): Float {
    return try {
        val brightness = Settings.System.getInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS
        )

        val normalized = 1f + (brightness * 32f) / 255f
        normalized
    } catch (e: Exception) {
        toastMessage(
            context = context,
            message = "Error getting brightness: ${e.message}"
        )
        17f
    }
}