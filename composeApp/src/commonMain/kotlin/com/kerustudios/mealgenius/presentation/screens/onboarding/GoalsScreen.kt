package com.kerustudios.mealgenius.presentation.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kerustudios.mealgenius.data.models.MealPlanningGoal
import com.kerustudios.mealgenius.presentation.navigation.Destinations

/**
 * Screen for selecting meal planning goals
 */
@Composable
fun GoalsScreen(
    navController: NavController
) {
    val onNext: () -> Unit = {
        navController.navigate(Destinations.Completion.route)
    }
    val onBack: () -> Unit = {
        navController.navigateUp()
    }
    val selectedGoals: List<MealPlanningGoal> = emptyList()
    val onSaveGoals: (List<MealPlanningGoal>) -> Unit = {}
    val goals = remember { mutableStateListOf<MealPlanningGoal>().apply { addAll(selectedGoals) } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "What are your meal planning goals?",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Select all that apply",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(MealPlanningGoal.values()) { goal ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = goals.contains(goal),
                        onCheckedChange = { checked ->
                            if (checked) {
                                goals.add(goal)
                            } else {
                                goals.remove(goal)
                            }
                        }
                    )
                    Text(
                        text = formatGoalName(goal),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onBack) {
                Text("Back")
            }

            Button(
                onClick = {
                    onSaveGoals(goals.toList())
                    onNext()
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
private fun formatGoalName(goal: MealPlanningGoal): String {
    return when (goal) {
        MealPlanningGoal.SAVE_TIME -> "Save Time"
        MealPlanningGoal.EAT_HEALTHIER -> "Eat Healthier"
        MealPlanningGoal.REDUCE_FOOD_WASTE -> "Reduce Food Waste"
        MealPlanningGoal.SAVE_MONEY -> "Save Money"
        MealPlanningGoal.LEARN_NEW_RECIPES -> "Learn New Recipes"
        MealPlanningGoal.WEIGHT_MANAGEMENT -> "Weight Management"
        MealPlanningGoal.DIETARY_RESTRICTIONS -> "Accommodate Dietary Restrictions"
    }
}