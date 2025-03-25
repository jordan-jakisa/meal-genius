package com.kerustudios.mealgenius.data.preferences

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * In-memory implementation of PreferencesDataStore
 * This is a temporary solution for platforms where DataStore is not fully supported
 */
class InMemoryPreferencesDataStore : PreferencesDataStore {
    // This implementation is intentionally left empty
    // The actual implementation will be provided by the platform-specific code

    // Create a dummy flow that never emits anything
    override val data: Flow<Preferences> = MutableStateFlow<Preferences>(null as Preferences)

    override suspend fun edit(transform: suspend (MutablePreferences) -> Unit) {
        // This implementation is intentionally left empty
        // The actual implementation will be provided by the platform-specific code
    }
}
