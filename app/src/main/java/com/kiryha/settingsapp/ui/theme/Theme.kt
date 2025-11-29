package com.kiryha.settingsapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.kiryha.api.SettingsRepository
import com.kiryha.api.ThemeMode
import org.koin.compose.koinInject

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val ExtraDarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Color.Black,
    surface = Color.Black,
    surfaceVariant = Color(0xFF1C1C1C),
    surfaceContainer = Color(0xFF121212),
    surfaceContainerHigh = Color(0xFF1C1C1C),
    surfaceContainerHighest = Color(0xFF252525),
    surfaceContainerLow = Color(0xFF0A0A0A),
    surfaceContainerLowest = Color.Black
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

private fun ColorScheme.toExtraDark(): ColorScheme {
    return this.copy(
        background = Color.Black,
        surface = Color.Black,
        surfaceVariant = Color(0xFF1C1C1C),
        surfaceContainer = Color(0xFF121212),
        surfaceContainerHigh = Color(0xFF1C1C1C),
        surfaceContainerHighest = Color(0xFF252525),
        surfaceContainerLow = Color(0xFF0A0A0A),
        surfaceContainerLowest = Color.Black
    )
}

@Composable
fun SettingsAppTheme(
    content: @Composable () -> Unit
) {
    val settingsRepository: SettingsRepository = koinInject()
    val settings by settingsRepository.userSettings.collectAsState(
        initial = com.kiryha.api.UserSettings()
    )

    val isDarkTheme = when (settings.themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.EXTRA_DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val isExtraDark = settings.themeMode == ThemeMode.EXTRA_DARK

    val dynamicColor = settings.isDynamicColorEnabled &&
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    val context = LocalContext.current

    val colorScheme = when {
        dynamicColor && isExtraDark -> {
            dynamicDarkColorScheme(context).toExtraDark()
        }
        dynamicColor && isDarkTheme -> {
            dynamicDarkColorScheme(context)
        }
        dynamicColor && !isDarkTheme -> {
            dynamicLightColorScheme(context)
        }
        isExtraDark -> ExtraDarkColorScheme
        isDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}