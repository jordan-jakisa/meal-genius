package com.kerustudios.mealgenius.di

import com.kerustudios.mealgenius.data.preferences.IosPreferencesDataStore
import com.kerustudios.mealgenius.data.preferences.PreferencesDataStore

/**
 * iOS-specific implementation of PlatformModule
 */
actual object PlatformModule {
    /**
     * Get the PreferencesDataStore instance for iOS
     */
    actual fun providePreferencesDataStore(): PreferencesDataStore {
        return IosPreferencesDataStore()
    }
}