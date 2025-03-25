package com.kerustudios.mealgenius.domain.usecases

/**
 * Class that groups all onboarding use cases for easier dependency injection
 */
data class OnboardingUseCases(
    val getUserPreferences: GetUserPreferencesUseCase,
    val savePreparedDishes: SavePreparedDishesUseCase,
    val saveMealPlanningGoals: SaveMealPlanningGoalsUseCase,
    val saveActivityLevel: SaveActivityLevelUseCase,
    val completeOnboarding: CompleteOnboardingUseCase,
    val isOnboardingCompleted: IsOnboardingCompletedUseCase
)