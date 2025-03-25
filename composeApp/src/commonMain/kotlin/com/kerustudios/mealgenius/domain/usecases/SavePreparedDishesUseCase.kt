package com.kerustudios.mealgenius.domain.usecases

import com.kerustudios.mealgenius.domain.repository.UserPreferencesRepository

/**
 * Use case for saving prepared dishes
 */
class SavePreparedDishesUseCase(
    private val repository: UserPreferencesRepository
) {
    suspend operator fun invoke(dishes: List<String>) {
        repository.savePreparedDishes(dishes)
    }
}