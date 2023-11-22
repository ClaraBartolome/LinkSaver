package com.example.linksaverapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.linksaverapp.Utils.ColorThemeOptions


@Composable
fun LinkSaverAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    color: ColorThemeOptions = ColorThemeOptions.Green,
    content: @Composable () -> Unit
) {
    val colorScheme = setColorScheme(isDarkTheme = !darkTheme, color = color)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Composable
private fun setColorScheme(isDarkTheme: Boolean, color: ColorThemeOptions): ColorScheme {
    return if (isDarkTheme) {
        lightColorScheme(
            primary = color.lightColor,
            onPrimary = Color.White,
            primaryContainer = color.containerLightColor,
            onPrimaryContainer = Color.Black,
            secondaryContainer = Color.White,
            onSecondaryContainer = Color.Black,
            onTertiaryContainer = color.lightColor,
            surface = Color.White,
            onSurface = Color.Black,
            background = Color.White,
            onBackground = Color.Black,
// ..
        )
    } else {
        darkColorScheme(
            primary = color.darkColor,
            onPrimary = Color.White,
            primaryContainer = Color.DarkGray,
            onPrimaryContainer = Color.White,
            secondaryContainer = Color.DarkGray,
            onSecondaryContainer = Color.White,
            onTertiaryContainer = color.lightColor,
            surface = blackBG,
            onSurface = Color.White,
            background = blackBG,
            onBackground = Color.White
// ..
        )
    }
}