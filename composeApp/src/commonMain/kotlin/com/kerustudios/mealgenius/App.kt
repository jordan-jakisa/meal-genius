package com.kerustudios.mealgenius

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import com.kerustudios.mealgenius.presentation.navigation.AppNavigation
import com.kerustudios.mealgenius.presentation.navigation.Destinations
import com.kerustudios.mealgenius.presentation.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        val isOnboardingCompleted = rememberSaveable { false }

        val startDestination = if (!isOnboardingCompleted) {
            Destinations.Welcome.route
        } else {
            Destinations.Home.route
        }

        Surface {
            AppNavigation(startDestination = startDestination)
        }
    }
}
