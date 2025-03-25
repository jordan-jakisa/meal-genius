package com.kerustudios.mealgenius.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import com.kerustudios.mealgenius.data.preferences.MutablePreferences

/**
 * Android implementation of PreferencesDataStore
 */
class AndroidPreferencesDataStore(private val context: Context) : PreferencesDataStore {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")
    }

    override val data: Flow<Preferences>
        get() = context.dataStore.data

    override suspend fun edit(transform: suspend (MutablePreferences) -> Unit) {
        context.dataStore.edit { preferences ->
            transform(AndroidMutablePreferences(preferences))
        }
    }
}

/**
 * Android implementation of MutablePreferences
 */
class AndroidMutablePreferences(private val preferences: Preferences) : MutablePreferences {
    override fun <T> set(key: Preferences.Key<T>, value: T) {
        //preferences[key] = value
        preferences.toMutablePreferences()[key] = value
    }
}
