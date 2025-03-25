package com.kerustudios.mealgenius.presentation.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
 * Screen displaying ingredients for a specific meal
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientListScreen(
    mealName: String = "Avocado Toast with Eggs",
    onBack: () -> Unit,
    onAddToShoppingList: () -> Unit
) {
    // Sample ingredient data
    val ingredients = listOf(
        Ingredient(
            name = "Avocado",
            quantity = "1 medium",
            calories = 234,
            protein = 2.9f,
            carbs = 12.5f,
            fat = 21.0f
        ),
        Ingredient(
            name = "Whole Grain Bread",
            quantity = "2 slices",
            calories = 138,
            protein = 7.0f,
            carbs = 23.0f,
            fat = 2.4f
        ),
        Ingredient(
            name = "Eggs",
            quantity = "2 large",
            calories = 143,
            protein = 12.6f,
            carbs = 0.7f,
            fat = 9.5f
        ),
        Ingredient(
            name = "Cherry Tomatoes",
            quantity = "1/4 cup, halved",
            calories = 13,
            protein = 0.7f,
            carbs = 2.8f,
            fat = 0.2f
        ),
        Ingredient(
            name = "Red Pepper Flakes",
            quantity = "1/4 teaspoon",
            calories = 2,
            protein = 0.1f,
            carbs = 0.3f,
            fat = 0.1f
        ),
        Ingredient(
            name = "Salt",
            quantity = "to taste",
            calories = 0,
            protein = 0.0f,
            carbs = 0.0f,
            fat = 0.0f
        ),
        Ingredient(
            name = "Black Pepper",
            quantity = "to taste",
            calories = 0,
            protein = 0.0f,
            carbs = 0.0f,
            fat = 0.0f
        ),
        Ingredient(
            name = "Olive Oil",
            quantity = "1 teaspoon",
            calories = 40,
            protein = 0.0f,
            carbs = 0.0f,
            fat = 4.5f
        )
    )
    
    // Calculate total nutritional values
    val totalCalories = ingredients.sumOf { it.calories }
    val totalProtein = ingredients.sumOf { it.protein.toDouble() }.toFloat()
    val totalCarbs = ingredients.sumOf { it.carbs.toDouble() }.toFloat()
    val totalFat = ingredients.sumOf { it.fat.toDouble() }.toFloat()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ingredients") },
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Meal name
            Text(
                text = mealName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            // Nutritional summary
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
                        text = "Nutritional Information",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        NutrientInfo(name = "Calories", value = "$totalCalories kcal")
                        NutrientInfo(name = "Protein", value = "$totalProtein g")
                        NutrientInfo(name = "Carbs", value = "$totalCarbs g")
                        NutrientInfo(name = "Fat", value = "$totalFat g")
                    }
                }
            }
            
            // Ingredients list
            Text(
                text = "Ingredients",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(ingredients) { ingredient ->
                    IngredientItem(ingredient = ingredient)
                }
            }
        }
    }
}

@Composable
private fun NutrientInfo(
    name: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun IngredientItem(
    ingredient: Ingredient
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = ingredient.name,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = ingredient.quantity,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
            
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${ingredient.calories} kcal",
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "P: ${ingredient.protein}g | C: ${ingredient.carbs}g | F: ${ingredient.fat}g",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

private data class Ingredient(
    val name: String,
    val quantity: String,
    val calories: Int,
    val protein: Float,
    val carbs: Float,
    val fat: Float
)