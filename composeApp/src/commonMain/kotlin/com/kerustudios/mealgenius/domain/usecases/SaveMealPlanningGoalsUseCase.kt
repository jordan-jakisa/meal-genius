package com.kerustudios.mealgenius.domain.usecases

import com.kerustudios.mealgenius.data.models.MealPlanningGoal
import com.kerustudios.mealgenius.domain.repository.UserPreferencesRepository

/**
 * Use case for saving meal planning goals
 */
class SaveMealPlanningGoalsUseCase(
    private val repository: UserPreferencesRepository
) {
    suspend operator fun invoke(goals: List<MealPlanningGoal>) {
        repository.saveMealPlanningGoals(goals)
    }
}