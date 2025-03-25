package com.kerustudios.mealgenius.data.preferences

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.kerustudios.mealgenius.data.models.ActivityLevel
import com.kerustudios.mealgenius.data.models.MealPlanningGoal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Repository for managing user preferences using DataStore
 */
class UserPreferencesRepository(
    private val dataStore: PreferencesDataStore
) {
    /**
     * Preference keys
     */
    object PreferenceKeys {
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val PREPARED_DISHES = stringSetPreferencesKey("prepared_dishes")
        val MEAL_PLANNING_GOALS = stringSetPreferencesKey("meal_planning_goals")
        val ACTIVITY_LEVEL = stringPreferencesKey("activity_level")
        val THEME = stringPreferencesKey("theme")
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
    }

    /**
     * Check if onboarding is completed
     */
    fun isOnboardingCompleted(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[PreferenceKeys.ONBOARDING_COMPLETED] ?: false
        }
    }

    /**
     * Complete onboarding
     */
    suspend fun completeOnboarding() {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.ONBOARDING_COMPLETED] = true
        }
    }

    /**
     * Get prepared dishes
     */
    fun getPreparedDishes(): Flow<List<String>> {
        return dataStore.data.map { preferences ->
            preferences[PreferenceKeys.PREPARED_DISHES]?.toList() ?: emptyList()
        }
    }

    /**
     * Save prepared dishes
     */
    suspend fun savePreparedDishes(dishes: List<String>) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.PREPARED_DISHES] = dishes.toSet()
        }
    }

    /**
     * Get meal planning goals
     */
    fun getMealPlanningGoals(): Flow<List<MealPlanningGoal>> {
        return dataStore.data.map { preferences ->
            preferences[PreferenceKeys.MEAL_PLANNING_GOALS]?.mapNotNull { goalString ->
                try {
                    MealPlanningGoal.valueOf(goalString)
                } catch (e: IllegalArgumentException) {
                    null
                }
            } ?: emptyList()
        }
    }

    /**
     * Save meal planning goals
     */
    suspend fun saveMealPlanningGoals(goals: List<MealPlanningGoal>) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.MEAL_PLANNING_GOALS] = goals.map { it.name }.toSet()
        }
    }

    /**
     * Get activity level
     */
    fun getActivityLevel(): Flow<ActivityLevel> {
        return dataStore.data.map { preferences ->
            preferences[PreferenceKeys.ACTIVITY_LEVEL]?.let { levelString ->
                try {
                    ActivityLevel.valueOf(levelString)
                } catch (e: IllegalArgumentException) {
                    ActivityLevel.MODERATE
                }
            } ?: ActivityLevel.MODERATE
        }
    }

    /**
     * Save activity level
     */
    suspend fun saveActivityLevel(level: ActivityLevel) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.ACTIVITY_LEVEL] = level.name
        }
    }

    /**
     * Get theme
     */
    fun getTheme(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[PreferenceKeys.THEME] ?: "system"
        }
    }

    /**
     * Save theme
     */
    suspend fun saveTheme(theme: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.THEME] = theme
        }
    }

    /**
     * Check if notifications are enabled
     */
    fun areNotificationsEnabled(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[PreferenceKeys.NOTIFICATIONS_ENABLED] ?: true
        }
    }

    /**
     * Enable or disable notifications
     */
    suspend fun setNotificationsEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.NOTIFICATIONS_ENABLED] = enabled
        }
    }
}

/**
 * Interface for DataStore to allow platform-specific implementations
 */
interface PreferencesDataStore {
    val data: Flow<Preferences>
    suspend fun edit(transform: suspend (MutablePreferences) -> Unit)
}

/**
 * Mutable preferences interface for editing preferences
 */
interface MutablePreferences {
    operator fun <T> set(key: Preferences.Key<T>, value: T)
}
