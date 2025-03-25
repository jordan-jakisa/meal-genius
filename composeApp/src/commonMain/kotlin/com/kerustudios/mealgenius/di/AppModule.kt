package com.kerustudios.mealgenius.di

import com.kerustudios.mealgenius.data.repositories.UserPreferencesRepositoryAdapter
import com.kerustudios.mealgenius.domain.repository.UserPreferencesRepository
import com.kerustudios.mealgenius.domain.usecases.CompleteOnboardingUseCase
import com.kerustudios.mealgenius.domain.usecases.GetUserPreferencesUseCase
import com.kerustudios.mealgenius.domain.usecases.IsOnboardingCompletedUseCase
import com.kerustudios.mealgenius.domain.usecases.OnboardingUseCases
import com.kerustudios.mealgenius.domain.usecases.SaveActivityLevelUseCase
import com.kerustudios.mealgenius.domain.usecases.SaveMealPlanningGoalsUseCase
import com.kerustudios.mealgenius.domain.usecases.SavePreparedDishesUseCase
import com.kerustudios.mealgenius.data.preferences.UserPreferencesRepository as DataStoreRepository

/**
 * Simple dependency injection module for the app
 */
object AppModule {
    /**
     * Get the UserPreferencesRepository instance
     */
    fun provideUserPreferencesRepository(): UserPreferencesRepository {
        val dataStore = PlatformModule.providePreferencesDataStore()
        val dataStoreRepository = DataStoreRepository(dataStore)
        return UserPreferencesRepositoryAdapter(dataStoreRepository)
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
