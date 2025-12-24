package com.example.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

// Modified reading mode color schemes with warmer, more eye-friendly colors
private val ReadingModeColorSchemeAtDark = darkColorScheme(
    primary = Color(0xFFE0C3A8),        // Warm beige for primary actions
    secondary = Color(0xFFBEAEA0),      // Muted taupe for secondary elements
    tertiary = Color(0xFFD9BFA9),       // Soft sand for tertiary elements
    background = Color(0xFF1A1816),     // Very dark brown (almost black)
    surface = Color(0xFF1A1816),        // Same as background for consistency
    onPrimary = Color(0xFF2D2A28),      // Dark brown for text on primary
    onSecondary = Color(0xFF2D2A28),    // Dark brown for text on secondary
    onTertiary = Color(0xFF2D2A28),     // Dark brown for text on tertiary
    onBackground = Color(0xFFE6DED5),   // Light cream for text on background
    onSurface = Color(0xFFE6DED5)       // Light cream for text on surface
)

private val ReadingModeColorSchemeAtLight = lightColorScheme(
    primary = Color(0xFFAE8F6E),        // Medium warm brown
    secondary = Color(0xFFCBB8A5),      // Light taupe
    tertiary = Color(0xFFD4BEA7),       // Warm sand
    background = Color(0xFFF5EFE6),     // Very light cream (paper-like)
    surface = Color(0xFFF5EFE6),        // Same as background for consistency
    onPrimary = Color(0xFFF9F5F0),      // Off-white for text on primary
    onSecondary = Color(0xFF3E352D),    // Dark brown for text on secondary
    onTertiary = Color(0xFF3E352D),     // Dark brown for text on tertiary
    onBackground = Color(0xFF3E352D),   // Dark brown for text on background
    onSurface = Color(0xFF3E352D)       // Dark brown for text on surface
)


@Composable
fun AllInOneTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    readingTheme: Boolean = false,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) {
                if (readingTheme) ReadingModeColorSchemeAtDark
                else dynamicDarkColorScheme(context)
            } else {
                if (readingTheme) ReadingModeColorSchemeAtLight
                else dynamicLightColorScheme(context)
            }
        }
        darkTheme && readingTheme -> ReadingModeColorSchemeAtDark
        !darkTheme && readingTheme -> ReadingModeColorSchemeAtLight
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
