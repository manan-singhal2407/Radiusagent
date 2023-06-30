package com.radiusagent.assignment.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

private val DarkColorScheme = darkColorScheme(
    background = PrimaryBackgroundColor,
    onBackground = PrimaryTextColor,
    secondary = SecondaryBackgroundColor,
)

private val LightColorScheme = lightColorScheme(
    background = PrimaryBackgroundColor,
    onBackground = PrimaryTextColor,
    secondary = SecondaryBackgroundColor,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = AppTypography,
        shapes = AppShapes
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryBackgroundColor)
        ) {
            content()
        }
    }
}