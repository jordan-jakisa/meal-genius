package com.kerustudios.mealgenius.domain.usecases

import com.kerustudios.mealgenius.data.models.UserPreferences
import com.kerustudios.mealgenius.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for getting user preferences
 */
class GetUserPreferencesUseCase(
    private val repository: UserPreferencesRepository
) {
    operator fun invoke(): Flow<UserPreferences> {
        return repository.getUserPreferences()
    }
}