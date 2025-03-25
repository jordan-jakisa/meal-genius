package com.kerustudios.mealgenius.presentation.screens.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.kerustudios.mealgenius.presentation.navigation.OnboardingScreen
import com.kerustudios.mealgenius.presentation.viewmodels.OnboardingViewModel

/**
 * Main composable for the onboarding flow
 */
@Composable
fun Onboarding(
    viewModel: OnboardingViewModel
) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    
    when (currentScreen) {
        OnboardingScreen.WELCOME -> WelcomeScreen(
            onNext = { viewModel.navigateToDishes() }
        )
        OnboardingScreen.DISHES -> DishesScreen(
            preparedDishes = viewModel.preparedDishes.collectAsState().value,
            onSaveDishes = { viewModel.savePreparedDishes(it) },
            onNext = { viewModel.navigateToGoals() },
            onBack = { viewModel.navigateToWelcome() }
        )
        OnboardingScreen.GOALS -> GoalsScreen(
            selectedGoals = viewModel.mealPlanningGoals.collectAsState().value,
            onSaveGoals = { viewModel.saveMealPlanningGoals(it) },
            onNext = { viewModel.navigateToActivity() },
            onBack = { viewModel.navigateToDishes() }
        )
        OnboardingScreen.ACTIVITY -> ActivityLevelScreen(
            selectedLevel = viewModel.activityLevel.collectAsState().value,
            onSaveActivityLevel = { viewModel.saveActivityLevel(it) },
            onNext = { 
                viewModel.navigateToCompletion()
            },
            onBack = { viewModel.navigateToGoals() }
        )
        OnboardingScreen.COMPLETION -> CompletionScreen(
            onComplete = { 
                viewModel.completeOnboarding()
                // Navigate to main app screen after onboarding
            }
        )
    }
}