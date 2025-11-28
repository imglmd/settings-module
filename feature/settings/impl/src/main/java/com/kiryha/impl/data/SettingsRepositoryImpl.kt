package com.kiryha.impl.data

import com.kiryha.api.AppLanguage
import com.kiryha.api.SettingsRepository
import com.kiryha.api.ThemeMode
import com.kiryha.api.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

internal class SettingsRepositoryImpl(
    private val dataStore: SettingsDataStore
): SettingsRepository {

    override val userSettings: Flow<UserSettings> = dataStore.userSettingsFlow

    override suspend fun getThemeMode(): ThemeMode {
        return userSettings.first().themeMode
    }

    override suspend fun setThemeMode(mode: ThemeMode) {
        dataStore.saveThemeMode(mode)
    }

    override suspend fun getLanguage(): AppLanguage {
        return userSettings.first().language
    }

    override suspend fun setLanguage(language: AppLanguage) {
        dataStore.saveLanguage(language)
    }

    override suspend fun setDynamicColorEnabled(enabled: Boolean) {
        dataStore.saveDynamicColorEnabled(enabled)
    }

    override suspend fun resetToDefaults() {
        dataStore.clear()
    }
}