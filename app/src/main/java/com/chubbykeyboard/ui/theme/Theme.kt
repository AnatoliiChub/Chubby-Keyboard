package com.chubbykeyboard.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Dark.Primary,
    onPrimary = Dark.OnPrimary,
    secondary = Dark.Secondary,
    onSecondary = Dark.OnSecondary,
    tertiary = Dark.Tertiary,
    onTertiary = Dark.OnTertiary,
    background = Dark.BackgroundColor,
    surface = Dark.Surface,
    onSurface = Dark.OnSurface,
    surfaceVariant = Dark.SurfaceVariant,
)

private val LightColorScheme = lightColorScheme(
    primary = Light.Primary,
    onPrimary = Light.OnPrimary,
    secondary = Light.Secondary,
    onSecondary = Light.OnSecondary,
    tertiary = Light.Tertiary,
    onTertiary = Light.OnTertiary,
    background = Light.BackgroundColor,
    surface = Light.Surface,
    onSurface = Light.OnSurface,
    surfaceVariant = Light.SurfaceVariant,
)

@Composable
fun ChubbyKeyboardTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme, typography = Typography, content = content
    )
}