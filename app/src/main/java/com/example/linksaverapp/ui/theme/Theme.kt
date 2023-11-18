package com.example.linksaverapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = mediumGreen,
    onPrimary = Color.White,
    primaryContainer = lightGreen,
    onPrimaryContainer = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
// ..
)
private val DarkColorScheme = darkColorScheme(
    primary = mediumGreen,
    onPrimary = Color.White,
    primaryContainer = lightGreen,
    onPrimaryContainer = Color.Black,
    surface = Color.Black,
    onSurface = Color.White,
    background = Color.Black,
    onBackground = Color.White
// ..
)

@Composable
fun LinkSaverAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme =
        if (!darkTheme) {
            LightColorScheme
        } else {
            DarkColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}