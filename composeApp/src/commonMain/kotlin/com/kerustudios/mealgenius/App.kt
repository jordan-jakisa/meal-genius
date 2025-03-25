package com.kerustudios.mealgenius

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.kerustudios.mealgenius.domain.usecases.OnboardingUseCases
import com.kerustudios.mealgenius.presentation.navigation.MainTabNavigatorWithBottomBar
import com.kerustudios.mealgenius.presentation.screens.onboarding.Onboarding
import com.kerustudios.mealgenius.presentation.viewmodels.OnboardingViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun App(onboardingUseCases: OnboardingUseCases) {
    MaterialTheme {
        val isOnboardingCompleted = onboardingUseCases.isOnboardingCompleted().collectAsState(initial = false)

        if (!isOnboardingCompleted.value) {
            // Show onboarding if not completed
            val onboardingViewModel = remember { OnboardingViewModel(onboardingUseCases) }
            Onboarding(viewModel = onboardingViewModel)
        } else {
            // Show main app content with bottom navigation MainTabNavigatorWithBottomBar()
            MainTabNavigatorWithBottomBar()
        }
    }
}

@Composable
@Preview
fun AppPreview() {
    // This is just for preview purposes
    MaterialTheme {
        MainTabNavigatorWithBottomBar()
    }
}
