package com.kerustudios.mealgenius.data.repositories

import com.kerustudios.mealgenius.data.models.ActivityLevel
import com.kerustudios.mealgenius.data.models.MealPlanningGoal
import com.kerustudios.mealgenius.data.models.UserPreferences
import com.kerustudios.mealgenius.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

/**
 * Implementation of UserPreferencesRepository that stores data in memory
 */
class UserPreferencesRepositoryImpl : UserPreferencesRepository {
    
    private val _userPreferences = MutableStateFlow(UserPreferences())
    
    override fun getUserPreferences(): Flow<UserPreferences> {
        return _userPreferences.asStateFlow()
    }
    
    override suspend fun savePreparedDishes(dishes: List<String>) {
        _userPreferences.value = _userPreferences.value.copy(preparedDishes = dishes)
    }
    
    override suspend fun saveMealPlanningGoals(goals: List<MealPlanningGoal>) {
        _userPreferences.value = _userPreferences.value.copy(mealPlanningGoals = goals)
    }
    
    override suspend fun saveActivityLevel(level: ActivityLevel) {
        _userPreferences.value = _userPreferences.value.copy(activityLevel = level)
    }
    
    override suspend fun completeOnboarding() {
        _userPreferences.value = _userPreferences.value.copy(onboardingCompleted = true)
    }
    
    override fun isOnboardingCompleted(): Flow<Boolean> {
        return _userPreferences.map { it.onboardingCompleted }
    }
}