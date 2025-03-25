package com.kerustudios.mealgenius.presentation.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Screen allowing users to shuffle meals within their current meal plan
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShuffleScreen(
    onBack: () -> Unit,
    onShuffleAll: () -> Unit,
    onShuffleSpecificDays: (List<String>) -> Unit,
    onShuffleMealTypes: (List<String>) -> Unit
) {
    var selectedOption by remember { mutableStateOf(ShuffleOption.ALL) }
    var selectedDays by remember { mutableStateOf(setOf<String>()) }
    var selectedMealTypes by remember { mutableStateOf(setOf<String>()) }
    
    val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    val mealTypes = listOf("Breakfast", "Lunch", "Dinner")
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shuffle Meals") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Shuffle Options",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            // Shuffle options
            Column(
                modifier = Modifier.selectableGroup(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ShuffleOptionItem(
                    text = "Shuffle All Meals",
                    selected = selectedOption == ShuffleOption.ALL,
                    onClick = { selectedOption = ShuffleOption.ALL }
                )
                
                ShuffleOptionItem(
                    text = "Shuffle Specific Days",
                    selected = selectedOption == ShuffleOption.SPECIFIC_DAYS,
                    onClick = { selectedOption = ShuffleOption.SPECIFIC_DAYS }
                )
                
                // Show day selection if "Shuffle Specific Days" is selected
                if (selectedOption == ShuffleOption.SPECIFIC_DAYS) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 32.dp),
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            days.forEach { day ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = selectedDays.contains(day),
                                        onCheckedChange = {
                                            selectedDays = if (it) {
                                                selectedDays + day
                                            } else {
                                                selectedDays - day
                                            }
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(day)
                                }
                            }
                        }
                    }
                }
                
                ShuffleOptionItem(
                    text = "Shuffle Meal Types",
                    selected = selectedOption == ShuffleOption.MEAL_TYPES,
                    onClick = { selectedOption = ShuffleOption.MEAL_TYPES }
                )
                
                // Show meal type selection if "Shuffle Meal Types" is selected
                if (selectedOption == ShuffleOption.MEAL_TYPES) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 32.dp),
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            mealTypes.forEach { mealType ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = selectedMealTypes.contains(mealType),
                                        onCheckedChange = {
                                            selectedMealTypes = if (it) {
                                                selectedMealTypes + mealType
                                            } else {
                                                selectedMealTypes - mealType
                                            }
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(mealType)
                                }
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Shuffle button
            Button(
                onClick = {
                    when (selectedOption) {
                        ShuffleOption.ALL -> onShuffleAll()
                        ShuffleOption.SPECIFIC_DAYS -> onShuffleSpecificDays(selectedDays.toList())
                        ShuffleOption.MEAL_TYPES -> onShuffleMealTypes(selectedMealTypes.toList())
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Shuffle")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Shuffle Meals")
            }
        }
    }
}

@Composable
private fun ShuffleOptionItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton
            )
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null // null because we're handling the click on the row
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

private enum class ShuffleOption {
    ALL,
    SPECIFIC_DAYS,
    MEAL_TYPES
}