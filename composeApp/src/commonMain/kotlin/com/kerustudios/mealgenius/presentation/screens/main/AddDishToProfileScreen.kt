package com.kerustudios.mealgenius.presentation.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Screen for adding a new dish to the user's profile
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDishToProfileScreen(
    onBack: () -> Unit,
    onSaveDish: (String, String, String, String, String) -> Unit
) {
    var dishName by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var preparationSteps by remember { mutableStateOf("") }
    var prepTime by remember { mutableStateOf("") }
    var difficulty by remember { mutableStateOf("Medium") }
    
    val difficultyOptions = listOf("Easy", "Medium", "Hard")
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Dish") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (dishName.isNotBlank() && ingredients.isNotBlank() && preparationSteps.isNotBlank()) {
                                onSaveDish(dishName, ingredients, preparationSteps, prepTime, difficulty)
                            }
                        },
                        enabled = dishName.isNotBlank() && ingredients.isNotBlank() && preparationSteps.isNotBlank()
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
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
                text = "Add a New Dish",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            // Dish name
            OutlinedTextField(
                value = dishName,
                onValueChange = { dishName = it },
                label = { Text("Dish Name") },
                placeholder = { Text("e.g., Spaghetti Bolognese") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            
            // Preparation time
            OutlinedTextField(
                value = prepTime,
                onValueChange = { prepTime = it },
                label = { Text("Preparation Time (minutes)") },
                placeholder = { Text("e.g., 30") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            
            // Difficulty
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Difficulty",
                    style = MaterialTheme.typography.titleMedium
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    difficultyOptions.forEach { option ->
                        DifficultyChip(
                            text = option,
                            selected = difficulty == option,
                            onClick = { difficulty = option },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            
            // Ingredients
            OutlinedTextField(
                value = ingredients,
                onValueChange = { ingredients = it },
                label = { Text("Ingredients") },
                placeholder = { Text("Enter each ingredient on a new line") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                maxLines = 10,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            
            // Preparation steps
            OutlinedTextField(
                value = preparationSteps,
                onValueChange = { preparationSteps = it },
                label = { Text("Preparation Steps") },
                placeholder = { Text("Enter each step on a new line") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                maxLines = 15
            )
            
            // Save button
            Button(
                onClick = {
                    if (dishName.isNotBlank() && ingredients.isNotBlank() && preparationSteps.isNotBlank()) {
                        onSaveDish(dishName, ingredients, preparationSteps, prepTime, difficulty)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = dishName.isNotBlank() && ingredients.isNotBlank() && preparationSteps.isNotBlank()
            ) {
                Text("Save Dish")
            }
            
            // Note about required fields
            if (dishName.isBlank() || ingredients.isBlank() || preparationSteps.isBlank()) {
                Text(
                    text = "* Dish name, ingredients, and preparation steps are required",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            
            // Add some space at the bottom for better scrolling experience
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun DifficultyChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.height(32.dp),
        shape = MaterialTheme.shapes.small,
        color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .padding(vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}