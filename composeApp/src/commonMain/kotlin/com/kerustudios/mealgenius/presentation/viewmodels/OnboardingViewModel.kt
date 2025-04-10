package com.kerustudios.mealgenius.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerustudios.mealgenius.data.models.ActivityLevel
import com.kerustudios.mealgenius.data.models.MealPlanningGoal
import com.kerustudios.mealgenius.domain.usecases.OnboardingUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the onboarding flow
 */
class OnboardingViewModel(
    private val onboardingUseCases: OnboardingUseCases
) : ViewModel() {
    
    
    // Prepared dishes
    private val _preparedDishes = MutableStateFlow<List<String>>(emptyList())
    val preparedDishes: StateFlow<List<String>> = _preparedDishes.asStateFlow()
    
    // Meal planning goals
    private val _mealPlanningGoals = MutableStateFlow<List<MealPlanningGoal>>(emptyList())
    val mealPlanningGoals: StateFlow<List<MealPlanningGoal>> = _mealPlanningGoals.asStateFlow()
    
    // Activity level
    private val _activityLevel = MutableStateFlow(ActivityLevel.MODERATE)
    val activityLevel: StateFlow<ActivityLevel> = _activityLevel.asStateFlow()

    // Save functions
    fun savePreparedDishes(dishes: List<String>) {
        _preparedDishes.value = dishes
        viewModelScope.launch {
            onboardingUseCases.savePreparedDishes(dishes)
        }
    }
    
    fun saveMealPlanningGoals(goals: List<MealPlanningGoal>) {
        _mealPlanningGoals.value = goals
        viewModelScope.launch {
            onboardingUseCases.saveMealPlanningGoals(goals)
        }
    }
    
    fun saveActivityLevel(level: ActivityLevel) {
        _activityLevel.value = level
        viewModelScope.launch {
            onboardingUseCases.saveActivityLevel(level)
        }
    }
    
    fun completeOnboarding() {
        viewModelScope.launch {
            onboardingUseCases.completeOnboarding()
        }
    }
}