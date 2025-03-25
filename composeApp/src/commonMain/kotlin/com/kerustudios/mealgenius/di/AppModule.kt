package com.kerustudios.mealgenius.di

import com.kerustudios.mealgenius.data.repositories.UserPreferencesRepositoryImpl
import com.kerustudios.mealgenius.domain.repository.UserPreferencesRepository
import com.kerustudios.mealgenius.domain.usecases.*

/**
 * Simple dependency injection module for the app
 */
object AppModule {
    /**
     * Get the UserPreferencesRepository instance
     */
    fun provideUserPreferencesRepository(): UserPreferencesRepository {
        return UserPreferencesRepositoryImpl()
    }
    
    /**
     * Get the OnboardingUseCases instance
     */
    fun provideOnboardingUseCases(): OnboardingUseCases {
        val repository = provideUserPreferencesRepository()
        return OnboardingUseCases(
            getUserPreferences = GetUserPreferencesUseCase(repository),
            savePreparedDishes = SavePreparedDishesUseCase(repository),
            saveMealPlanningGoals = SaveMealPlanningGoalsUseCase(repository),
            saveActivityLevel = SaveActivityLevelUseCase(repository),
            completeOnboarding = CompleteOnboardingUseCase(repository),
            isOnboardingCompleted = IsOnboardingCompletedUseCase(repository)
        )
    }
}