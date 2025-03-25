package com.kerustudios.mealgenius

import androidx.compose.ui.window.ComposeUIViewController
import com.kerustudios.mealgenius.di.AppModule

fun MainViewController() = ComposeUIViewController { 
    val onboardingUseCases = AppModule.provideOnboardingUseCases()
    App(onboardingUseCases) 
}
