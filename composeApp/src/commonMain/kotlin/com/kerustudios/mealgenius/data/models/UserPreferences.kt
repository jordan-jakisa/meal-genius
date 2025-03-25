package com.kerustudios.mealgenius.data.models

/**
 * Data class representing user preferences collected during onboarding
 */
data class UserPreferences(
    val id: Int = 0,
    val onboardingCompleted: Boolean = false,
    val preparedDishes: List<String> = emptyList(),
    val mealPlanningGoals: List<MealPlanningGoal> = emptyList(),
    val activityLevel: ActivityLevel = ActivityLevel.MODERATE
)

/**
 * Enum representing different meal planning goals
 */
enum class MealPlanningGoal {
    SAVE_TIME,
    EAT_HEALTHIER,
    REDUCE_FOOD_WASTE,
    SAVE_MONEY,
    LEARN_NEW_RECIPES,
    WEIGHT_MANAGEMENT,
    DIETARY_RESTRICTIONS
}

/**
 * Enum representing different activity levels
 */
enum class ActivityLevel {
    SEDENTARY,
    LIGHT,
    MODERATE,
    ACTIVE,
    VERY_ACTIVE
}