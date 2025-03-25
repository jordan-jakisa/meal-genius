package com.kerustudios.mealgenius.di

import android.content.Context
import com.kerustudios.mealgenius.data.preferences.AndroidPreferencesDataStore
import com.kerustudios.mealgenius.data.preferences.InMemoryPreferencesDataStore
import com.kerustudios.mealgenius.data.preferences.PreferencesDataStore

/**
 * Android-specific implementation of PlatformModule
 */
actual object PlatformModule {
    private var context: Context? = null

    /**
     * Initialize the module with the Android context
     */
    fun initialize(context: Context) {
        this.context = context
    }

    /**
     * Get the PreferencesDataStore instance for Android
     */
    actual fun providePreferencesDataStore(): PreferencesDataStore {
        val ctx = context
        return if (ctx != null) {
            AndroidPreferencesDataStore(ctx)
        } else {
            // Fallback to in-memory implementation if context is not available
            InMemoryPreferencesDataStore()
        }
    }
}