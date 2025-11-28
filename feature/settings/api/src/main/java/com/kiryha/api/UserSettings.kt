package com.kiryha.api

data class UserSettings(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val language: AppLanguage = AppLanguage.ENGLISH,
    val isDynamicColorEnabled: Boolean = true,
)
