package com.kiryha.impl.domain

import com.kiryha.api.AppLanguage
import com.kiryha.api.SettingsRepository
import com.kiryha.api.ThemeMode
import com.kiryha.api.UserSettings
import kotlinx.coroutines.flow.Flow

/**
 * Use Case для получения настроек
 */
internal class ObserveUserSettingsUseCase(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<UserSettings> = repository.userSettings
}

/**
 * Use Case для изменения темы
 */
internal class UpdateThemeModeUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(mode: ThemeMode) {
        repository.setThemeMode(mode)
    }
}

/**
 * Use Case для изменения языка
 */
internal class UpdateLanguageUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(language: AppLanguage) {
        repository.setLanguage(language)
    }
}

/**
 * Use Case для включения/выключения динамических цветов
 */
internal class UpdateDynamicColorUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(enabled: Boolean) {
        repository.setDynamicColorEnabled(enabled)
    }
}