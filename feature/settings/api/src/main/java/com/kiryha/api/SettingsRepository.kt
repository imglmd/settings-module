package com.kiryha.api

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val userSettings: Flow<UserSettings>

    suspend fun getThemeMode(): ThemeMode
    suspend fun setThemeMode(mode: ThemeMode)

    suspend fun getLanguage(): AppLanguage
    suspend fun setLanguage(language: AppLanguage)

    suspend fun setDynamicColorEnabled(enabled: Boolean)

    suspend fun resetToDefaults()
}