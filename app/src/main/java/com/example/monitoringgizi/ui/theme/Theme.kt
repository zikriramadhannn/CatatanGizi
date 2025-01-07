package com.example.monitoringgizi.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Mengimpor warna minimalis yang telah dideklarasikan di color.kt
import com.example.monitoringgizi.ui.theme.PrimaryColor
import com.example.monitoringgizi.ui.theme.SecondaryColor
import com.example.monitoringgizi.ui.theme.BackgroundColor
import com.example.monitoringgizi.ui.theme.SurfaceColor
import com.example.monitoringgizi.ui.theme.OnPrimaryColor
import com.example.monitoringgizi.ui.theme.OnSecondaryColor
import com.example.monitoringgizi.ui.theme.OnBackgroundColor
import com.example.monitoringgizi.ui.theme.OnSurfaceColor

// Settingan tema yang digunakan di aplikasi dengan tema minimalis
private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    background = BackgroundColor,
    surface = SurfaceColor,
    onPrimary = OnPrimaryColor,
    onSecondary = OnSecondaryColor,
    onBackground = OnBackgroundColor,
    onSurface = OnSurfaceColor
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    background = BackgroundColor,
    surface = SurfaceColor,
    onPrimary = OnPrimaryColor,
    onSecondary = OnSecondaryColor,
    onBackground = OnBackgroundColor,
    onSurface = OnSurfaceColor
)

@Composable
fun MonitoringGiziTheme(
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
