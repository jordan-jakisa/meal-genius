package com.kerustudios.mealgenius

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.kerustudios.mealgenius.di.AppModule

fun main() = application {
    val onboardingUseCases = AppModule.provideOnboardingUseCases()

    Window(
        onCloseRequest = ::exitApplication,
        title = "MealGenius",
    ) {
        App(onboardingUseCases)
    }
}
