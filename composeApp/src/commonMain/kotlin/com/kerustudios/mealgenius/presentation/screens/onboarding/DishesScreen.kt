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
import com.kerustudios.mealgenius.presentation.navigation.Destinations

/**
 * Screen for selecting prepared dishes
 */
@Composable
fun DishesScreen(
    navController: NavController,
) {
    val preparedDishes: List<String> = emptyList()
    val onSaveDishes: (List<String>) -> Unit = {}
    val selectedDishes = remember { mutableStateListOf<String>().apply { addAll(preparedDishes) } }
    val onNext: () -> Unit = {
        navController.navigate(Destinations.Goals.route)
    }
    val onBack: () -> Unit = {
        navController.navigateUp()
    }
    
    // Sample dishes if the list is empty
    val availableDishes = if (preparedDishes.isEmpty()) {
        listOf("Pasta", "Pizza", "Salad", "Soup", "Stir Fry", "Sandwich", "Rice Bowl", "Curry")
    } else {
        preparedDishes
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "What dishes do you typically prepare?",
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
            items(availableDishes) { dish ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = selectedDishes.contains(dish),
                        onCheckedChange = { checked ->
                            if (checked) {
                                selectedDishes.add(dish)
                            } else {
                                selectedDishes.remove(dish)
                            }
                        }
                    )
                    Text(
                        text = dish,
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
                    onSaveDishes(selectedDishes.toList())
                    onNext()
                }
            ) {
                Text("Next")
            }
        }
    }
}