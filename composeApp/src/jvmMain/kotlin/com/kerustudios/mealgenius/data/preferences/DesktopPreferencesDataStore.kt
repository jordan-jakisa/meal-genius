package com.kerustudios.mealgenius.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import java.io.File

/**
 * Desktop implementation of PreferencesDataStore
 */
class DesktopPreferencesDataStore : PreferencesDataStore {
    companion object {
        private val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create {
            File(System.getProperty("user.home") + "/.mealgenius/preferences.preferences_pb")
        }
    }

    override val data: Flow<Preferences>
        get() = dataStore.data

    override suspend fun edit(transform: suspend (MutablePreferences) -> Unit) {
        dataStore.edit { preferences ->
            transform(DesktopMutablePreferences(preferences))
        }
    }
}

/**
 * Desktop implementation of MutablePreferences
 */
class DesktopMutablePreferences(private val preferences: Preferences.MutablePreferences) : MutablePreferences {
    override fun <T> set(key: Preferences.Key<T>, value: T) {
        preferences[key] = value
    }
}