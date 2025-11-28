package com.kiryha.settingsapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.kiryha.api.SettingsRepository
import com.kiryha.api.ThemeMode
import com.kiryha.api.UserSettings
import org.koin.compose.koinInject

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
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    val dynamicColor = settings.isDynamicColorEnabled &&
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    val context = LocalContext.current

    val colorScheme = when {
        dynamicColor && isDarkTheme -> {
            dynamicDarkColorScheme(context)
        }
        dynamicColor && !isDarkTheme -> {
            dynamicLightColorScheme(context)
        }

        isDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}