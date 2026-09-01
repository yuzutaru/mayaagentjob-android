package com.maya.jobs.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Emerald = Color(0xFF22C55E)
private val Indigo = Color(0xFF6366F1)

private val LightColors = lightColorScheme(
    primary = Emerald,
    secondary = Indigo,
)

private val DarkColors = darkColorScheme(
    primary = Emerald,
    secondary = Indigo,
)

@Composable
fun MayaTheme(content: @Composable () -> Unit) {
    val colors = if (isSystemInDarkTheme()) DarkColors else LightColors
    MaterialTheme(colorScheme = colors, content = content)
}
