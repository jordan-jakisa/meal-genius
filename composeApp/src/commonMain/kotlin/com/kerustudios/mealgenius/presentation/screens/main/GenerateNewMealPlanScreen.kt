package com.kerustudios.mealgenius.presentation.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Screen for generating a new meal plan
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenerateNewMealPlanScreen(
    onBack: () -> Unit,
    onGeneratePlan: (Boolean, List<String>, List<String>) -> Unit
) {
    var regenerateAll by remember { mutableStateOf(true) }
    var selectedDays by remember { mutableStateOf(setOf<String>()) }
    var selectedMealTypes by remember { mutableStateOf(setOf<String>()) }
    
    val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    val mealTypes = listOf("Breakfast", "Lunch", "Dinner")
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Generate New Meal Plan") },
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Customize Your New Meal Plan",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            // Option to regenerate the entire plan or customize
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    checked = regenerateAll,
                    onCheckedChange = { 
                        regenerateAll = it
                        if (it) {
                            // Clear selections when switching to regenerate all
                            selectedDays = emptySet()
                            selectedMealTypes = emptySet()
                        }
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Regenerate entire meal plan",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            
            // Show customization options if not regenerating all
            if (!regenerateAll) {
                Divider()
                
                // Days selection
                Text(
                    text = "Select Days to Regenerate",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                
                Column(
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
                
                Divider()
                
                // Meal types selection
                Text(
                    text = "Select Meal Types to Regenerate",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                
                Column(
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
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Generate button
            Button(
                onClick = {
                    onGeneratePlan(
                        regenerateAll,
                        selectedDays.toList(),
                        selectedMealTypes.toList()
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = regenerateAll || (selectedDays.isNotEmpty() && selectedMealTypes.isNotEmpty())
            ) {
                Text("Generate Meal Plan")
            }
            
            // Note about customization
            if (!regenerateAll && (selectedDays.isEmpty() || selectedMealTypes.isEmpty())) {
                Text(
                    text = "Please select at least one day and one meal type",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}