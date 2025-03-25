package com.kerustudios.mealgenius.di

import com.kerustudios.mealgenius.data.preferences.PreferencesDataStore

/**
 * Platform-specific module for dependency injection
 */
expect object PlatformModule {
    /**
     * Get the PreferencesDataStore instance for the current platform
     */
    fun providePreferencesDataStore(): PreferencesDataStore
}