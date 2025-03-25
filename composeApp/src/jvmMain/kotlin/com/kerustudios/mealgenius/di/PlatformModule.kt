package com.kerustudios.mealgenius.di

import com.kerustudios.mealgenius.data.preferences.DesktopPreferencesDataStore
import com.kerustudios.mealgenius.data.preferences.PreferencesDataStore

/**
 * Desktop-specific implementation of PlatformModule
 */
actual object PlatformModule {
    /**
     * Get the PreferencesDataStore instance for Desktop
     */
    actual fun providePreferencesDataStore(): PreferencesDataStore {
        return DesktopPreferencesDataStore()
    }
}