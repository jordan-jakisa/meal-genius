package com.kerustudios.mealgenius.presentation.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Screen displaying detailed information about a specific meal plan
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealPlanDetailsScreen(
    dayOfWeek: String = "Monday",
    onBack: () -> Unit,
    onViewIngredients: () -> Unit,
    onAddToShoppingList: () -> Unit,
    onEditMeal: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meal Plan Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onAddToShoppingList) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Add to Shopping List")
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
                text = dayOfWeek,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            // Breakfast section
            MealSection(
                mealType = "Breakfast",
                mealName = "Avocado Toast with Eggs",
                calories = "450",
                prepTime = "15 min",
                difficulty = "Easy",
                onViewIngredients = onViewIngredients,
                onEditMeal = onEditMeal
            )
            
            // Lunch section
            MealSection(
                mealType = "Lunch",
                mealName = "Chicken Caesar Salad",
                calories = "550",
                prepTime = "20 min",
                difficulty = "Medium",
                onViewIngredients = onViewIngredients,
                onEditMeal = onEditMeal
            )
            
            // Dinner section
            MealSection(
                mealType = "Dinner",
                mealName = "Grilled Salmon with Vegetables",
                calories = "650",
                prepTime = "30 min",
                difficulty = "Medium",
                onViewIngredients = onViewIngredients,
                onEditMeal = onEditMeal
            )
        }
    }
}

@Composable
private fun MealSection(
    mealType: String,
    mealName: String,
    calories: String,
    prepTime: String,
    difficulty: String,
    onViewIngredients: () -> Unit,
    onEditMeal: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = mealType,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = mealName,
                fontSize = 16.sp
            )
            
            Divider()
            
            // Meal details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DetailItem(label = "Calories", value = calories)
                DetailItem(label = "Prep Time", value = prepTime)
                DetailItem(label = "Difficulty", value = difficulty)
            }
            
            Divider()
            
            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onViewIngredients) {
                    Text("View Ingredients")
                }
                TextButton(onClick = onEditMeal) {
                    Text("Edit Meal")
                }
            }
        }
    }
}

@Composable
private fun DetailItem(
    label: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            fontWeight = FontWeight.Medium
        )
    }
}