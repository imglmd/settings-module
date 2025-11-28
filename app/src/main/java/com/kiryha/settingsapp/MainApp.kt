package com.kiryha.settingsapp

import android.app.Application
import com.kiryha.impl.di.settingsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(settingsModule)
        }
    }
}