package com.kerustudios.mealgenius

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kerustudios.mealgenius.di.PlatformModule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the PlatformModule with the Android context
        PlatformModule.initialize(applicationContext)

        setContent {
            App()
        }
    }
}