package com.kerustudios.mealgenius.presentation.navigation


sealed class Destinations(val route: String) {
    object Welcome : Destinations("welcome")

    object Dishes : Destinations("dishes")

    object Goals : Destinations("goals")

    object ActivityLevelDestination : Destinations("activity_level_destination")

    object Completion : Destinations("completion")

    object Home : Destinations("home")

    object Chatbot : Destinations("chatbot")

    object ShoppingList : Destinations("shoppinglist")

    object MealPlanHistory : Destinations("mealplanhistory")
}