package com.kerustudios.mealgenius.domain.repository

import com.kerustudios.mealgenius.data.models.ActivityLevel
import com.kerustudios.mealgenius.data.models.MealPlanningGoal
import com.kerustudios.mealgenius.data.models.UserPreferences
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing user preferences
 */
interface UserPreferencesRepository {
    /**
     * Get the user preferences as a Flow
     */
    fun getUserPreferences(): Flow<UserPreferences>
    
    /**
     * Save the prepared dishes
     */
    suspend fun savePreparedDishes(dishes: List<String>)
    
    /**
     * Save the meal planning goals
     */
    suspend fun saveMealPlanningGoals(goals: List<MealPlanningGoal>)
    
    /**
     * Save the activity level
     */
    suspend fun saveActivityLevel(level: ActivityLevel)
    
    /**
     * Mark the onboarding as completed
     */
    suspend fun completeOnboarding()
    
    /**
     * Check if onboarding is completed
     */
    fun isOnboardingCompleted(): Flow<Boolean>
}