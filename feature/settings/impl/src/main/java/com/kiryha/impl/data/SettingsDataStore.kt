package com.kiryha.impl.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kiryha.api.AppLanguage
import com.kiryha.api.ThemeMode
import com.kiryha.api.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/**
 * Класс для сохранения настроек через DataStore
 */
internal class SettingsDataStore(private val context: Context) {

    private val dataStore = context.settingsDataStore

    companion object {
        private val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
        private val LANGUAGE_KEY = stringPreferencesKey("language")
        private val DYNAMIC_COLOR_KEY = booleanPreferencesKey("dynamic_color")
    }

    /**
     * Flow с настройками пользователя
     */
    val userSettingsFlow: Flow<UserSettings> = dataStore.data.map { preferences ->
        UserSettings(
            themeMode = preferences[THEME_MODE_KEY]?.let {
                ThemeMode.valueOf(it)
            } ?: ThemeMode.SYSTEM,

            language = preferences[LANGUAGE_KEY]?.let {
                AppLanguage.fromCode(it)
            } ?: AppLanguage.ENGLISH,

            isDynamicColorEnabled = preferences[DYNAMIC_COLOR_KEY] ?: true,
        )
    }

    /**
     * Сохранить режим темы
     */
    suspend fun saveThemeMode(mode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = mode.name
        }
    }

    /**
     * Сохранить язык
     */
    suspend fun saveLanguage(language: AppLanguage) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language.code
        }
    }

    /**
     * Сохранить настройку динамических цветов
     */
    suspend fun saveDynamicColorEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[DYNAMIC_COLOR_KEY] = enabled
        }
    }


    /**
     * Очистить все настройки
     */
    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
