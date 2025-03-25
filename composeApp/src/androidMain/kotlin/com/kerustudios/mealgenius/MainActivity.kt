package com.kerustudios.mealgenius

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kerustudios.mealgenius.di.AppModule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onboardingUseCases = AppModule.provideOnboardingUseCases()

        setContent {
            App(onboardingUseCases)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    // This is just for preview purposes
    // In a real app, we would use a mock or fake implementation
    App(AppModule.provideOnboardingUseCases())
}
