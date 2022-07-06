package com.test.androiddevelopersexample.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = GlobalPrimary400,
    primaryVariant = Indigo600,
    onPrimary = Color.White,
    secondary = Color.White,
    surface = Grey900,
    background = Grey900
)

private val LightColorPalette = lightColors(
    primary = GlobalPrimary400,
    primaryVariant = Indigo600,
    onPrimary = Color.White,
    secondary = Color.Black,
    surface = Grey25,
    onSurface = Grey800,
    background = Grey25,
)

@Composable
fun AstroPayTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes
    ) {
        Surface {
            content()
        }
    }
}