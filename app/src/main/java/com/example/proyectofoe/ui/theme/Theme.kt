package com.example.proyectofoe.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = FoePrimaryContainer,
    secondary = FoePrimaryContainer,
    tertiary = FoePrimaryContainer
)

private val LightColorScheme = lightColorScheme(
    primary = FoePrimary,
    onPrimary = FoeOnPrimary,
    primaryContainer = FoePrimaryContainer,
    onPrimaryContainer = FoeOnPrimaryContainer,
    secondary = FoePrimary,
    onSecondary = FoeOnPrimary,
    secondaryContainer = FoePrimaryContainer,
    onSecondaryContainer = FoeOnPrimaryContainer,
    tertiary = FoePrimary,
    background = FoeSurface,
    surface = FoeSurface,
    onSurface = FoeOnSurface,
    onSurfaceVariant = FoeOnSurfaceVariant,
    error = FoeError
)

@Composable
fun ProyectoFOETheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
