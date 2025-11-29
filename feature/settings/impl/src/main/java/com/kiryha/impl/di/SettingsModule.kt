package com.kiryha.impl.di

import com.kiryha.api.SettingsRepository
import com.kiryha.impl.data.SettingsDataStore
import com.kiryha.impl.data.SettingsRepositoryImpl
import com.kiryha.impl.domain.ObserveUserSettingsUseCase
import com.kiryha.impl.domain.ResetToDefaultsUseCase
import com.kiryha.impl.domain.UpdateDynamicColorUseCase
import com.kiryha.impl.domain.UpdateLanguageUseCase
import com.kiryha.impl.domain.UpdateThemeModeUseCase
import com.kiryha.impl.presentation.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {

    single { SettingsDataStore(androidContext()) }

    single<SettingsRepository> {
        SettingsRepositoryImpl(get())
    }

    factory { ObserveUserSettingsUseCase(get()) }
    factory { UpdateThemeModeUseCase(get()) }
    factory { UpdateLanguageUseCase(get()) }
    factory { UpdateDynamicColorUseCase(get()) }
    factory { ResetToDefaultsUseCase(get()) }

    viewModel {
        SettingsViewModel(
            observeUserSettings = get(),
            updateThemeMode = get(),
            updateLanguage = get(),
            updateDynamicColor = get(),
            resetToDefaults = get()
        )
    }
}