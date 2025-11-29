package com.kiryha.impl.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiryha.api.AppLanguage
import com.kiryha.api.ThemeMode
import com.kiryha.api.UserSettings
import com.kiryha.impl.domain.ObserveUserSettingsUseCase
import com.kiryha.impl.domain.ResetToDefaultsUseCase
import com.kiryha.impl.domain.UpdateDynamicColorUseCase
import com.kiryha.impl.domain.UpdateLanguageUseCase
import com.kiryha.impl.domain.UpdateThemeModeUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class SettingsViewModel(
    private val observeUserSettings: ObserveUserSettingsUseCase,
    private val updateThemeMode: UpdateThemeModeUseCase,
    private val updateLanguage: UpdateLanguageUseCase,
    private val updateDynamicColor: UpdateDynamicColorUseCase,
    private val resetToDefaults: ResetToDefaultsUseCase
) : ViewModel() {

    var showResetDialog by mutableStateOf(false)
        private set

    val userSettings: StateFlow<UserSettings> = observeUserSettings()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserSettings()
        )

    fun onThemeModeChanged(mode: ThemeMode) {
        viewModelScope.launch {
            updateThemeMode(mode)
        }
    }

    fun onLanguageChanged(language: AppLanguage) {
        viewModelScope.launch {
            updateLanguage(language)
        }
    }

    fun onDynamicColorChanged(enabled: Boolean) {
        viewModelScope.launch {
            updateDynamicColor(enabled)
        }
    }

    fun showResetDialog() {
        showResetDialog = true
    }

    fun hideResetDialog() {
        showResetDialog = false
    }

    fun resetSettings(){
        viewModelScope.launch {
            resetToDefaults()
            hideResetDialog()
        }
    }
}