package com.kerustudios.mealgenius.presentation.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kerustudios.mealgenius.data.models.ActivityLevel
import com.kerustudios.mealgenius.presentation.navigation.Destinations

/**
 * Screen for selecting activity level
 */
@Composable
fun ActivityLevelScreen(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "What is your activity level?",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "This helps us tailor meal suggestions to your energy needs",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Column(
            modifier = Modifier
                .selectableGroup()
                .weight(1f)
        ) {
            ActivityLevel.values().forEach { level ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (true),
                            onClick = { }
                        )
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (true),
                        onClick = null // null because we're handling selection in the Row's selectable modifier
                    )
                    Column(
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text(
                            text = formatActivityLevel(level),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = getActivityLevelDescription(level),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { navController.navigate(Destinations.Welcome.route) }) {
                Text("Back")
            }
            
            Button(
                onClick = {
                    navController.navigate(Destinations.ActivityLevelDestination.route)
                }
            ) {
                Text("Next")
            }
        }
    }
}

/**
 * Formats the enum value to a more readable string
 */
private fun formatActivityLevel(level: ActivityLevel): String {
    return when (level) {
        ActivityLevel.SEDENTARY -> "Sedentary"
        ActivityLevel.LIGHT -> "Lightly Active"
        ActivityLevel.MODERATE -> "Moderately Active"
        ActivityLevel.ACTIVE -> "Active"
        ActivityLevel.VERY_ACTIVE -> "Very Active"
    }
}

/**
 * Returns a description for each activity level
 */
private fun getActivityLevelDescription(level: ActivityLevel): String {
    return when (level) {
        ActivityLevel.SEDENTARY -> "Little to no exercise"
        ActivityLevel.LIGHT -> "Light exercise 1-3 days/week"
        ActivityLevel.MODERATE -> "Moderate exercise 3-5 days/week"
        ActivityLevel.ACTIVE -> "Hard exercise 6-7 days/week"
        ActivityLevel.VERY_ACTIVE -> "Very hard exercise & physical job or training twice a day"
    }
}