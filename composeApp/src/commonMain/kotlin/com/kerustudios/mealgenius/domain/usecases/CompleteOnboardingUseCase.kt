package com.kerustudios.mealgenius.domain.usecases

import com.kerustudios.mealgenius.domain.repository.UserPreferencesRepository

/**
 * Use case for completing the onboarding process
 */
class CompleteOnboardingUseCase(
    private val repository: UserPreferencesRepository
) {
    suspend operator fun invoke() {
        repository.completeOnboarding()
    }
}