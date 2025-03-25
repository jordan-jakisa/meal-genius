package com.kerustudios.mealgenius.domain.usecases

import com.kerustudios.mealgenius.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for checking if onboarding is completed
 */
class IsOnboardingCompletedUseCase(
    private val repository: UserPreferencesRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return repository.isOnboardingCompleted()
    }
}