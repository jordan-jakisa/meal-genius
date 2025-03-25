package com.kerustudios.mealgenius.domain.usecases

import com.kerustudios.mealgenius.data.models.ActivityLevel
import com.kerustudios.mealgenius.domain.repository.UserPreferencesRepository

/**
 * Use case for saving activity level
 */
class SaveActivityLevelUseCase(
    private val repository: UserPreferencesRepository
) {
    suspend operator fun invoke(level: ActivityLevel) {
        repository.saveActivityLevel(level)
    }
}