package com.kerustudios.mealgenius.data.repositories

import com.kerustudios.mealgenius.data.models.ActivityLevel
import com.kerustudios.mealgenius.data.models.MealPlanningGoal
import com.kerustudios.mealgenius.data.models.UserPreferences
import com.kerustudios.mealgenius.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.kerustudios.mealgenius.data.preferences.UserPreferencesRepository as DataStoreRepository

/**
 * Adapter class that implements the UserPreferencesRepository interface and delegates to the UserPreferencesRepository class in the data.preferences package
 */
class UserPreferencesRepositoryAdapter(
    private val dataStoreRepository: DataStoreRepository
) : UserPreferencesRepository {

    override fun getUserPreferences(): Flow<UserPreferences> {
        return dataStoreRepository.getPreparedDishes().map { dishes ->
            UserPreferences(
                preparedDishes = dishes,
                mealPlanningGoals = emptyList(), // This will be updated when we get the meal planning goals
                activityLevel = ActivityLevel.MODERATE, // This will be updated when we get the activity level
                onboardingCompleted = false // This will be updated when we check if onboarding is completed
            )
        }
    }

    override suspend fun savePreparedDishes(dishes: List<String>) {
        dataStoreRepository.savePreparedDishes(dishes)
    }

    override suspend fun saveMealPlanningGoals(goals: List<MealPlanningGoal>) {
        dataStoreRepository.saveMealPlanningGoals(goals)
    }

    override suspend fun saveActivityLevel(level: ActivityLevel) {
        dataStoreRepository.saveActivityLevel(level)
    }

    override suspend fun completeOnboarding() {
        dataStoreRepository.completeOnboarding()
    }

    override fun isOnboardingCompleted(): Flow<Boolean> {
        return dataStoreRepository.isOnboardingCompleted()
    }
}