package com.kerustudios.mealgenius.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kerustudios.mealgenius.presentation.screens.main.ChatbotScreen
import com.kerustudios.mealgenius.presentation.screens.main.HomeScreen
import com.kerustudios.mealgenius.presentation.screens.main.MealPlanHistoryScreen
import com.kerustudios.mealgenius.presentation.screens.main.ShoppingListScreen
import com.kerustudios.mealgenius.presentation.screens.onboarding.ActivityLevelScreen
import com.kerustudios.mealgenius.presentation.screens.onboarding.CompletionScreen
import com.kerustudios.mealgenius.presentation.screens.onboarding.DishesScreen
import com.kerustudios.mealgenius.presentation.screens.onboarding.GoalsScreen
import com.kerustudios.mealgenius.presentation.screens.onboarding.WelcomeScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize()
    ) {

        composable(Destinations.Welcome.route) {
            WelcomeScreen(navController)
        }

        composable(Destinations.ActivityLevelDestination.route) {
            ActivityLevelScreen(navController)
        }

        composable(Destinations.Chatbot.route) {
            ChatbotScreen(navController)
        }

        composable(Destinations.Dishes.route) {
            DishesScreen(navController)
        }

        composable(Destinations.Goals.route) {
            GoalsScreen(navController)
        }

        composable(Destinations.Completion.route) {
            CompletionScreen(navController)
        }

        composable(Destinations.Home.route) {
            HomeScreen(navController)
        }

        composable(Destinations.MealPlanHistory.route) {
            MealPlanHistoryScreen(navController)
        }

        composable(Destinations.ShoppingList.route) {
            ShoppingListScreen(navController)
        }
    }
}