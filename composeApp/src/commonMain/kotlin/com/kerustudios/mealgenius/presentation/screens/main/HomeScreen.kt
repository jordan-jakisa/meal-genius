package com.kerustudios.mealgenius.presentation.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Home screen displaying the current meal plan and navigation options
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onViewMealPlanDetails: () -> Unit,
    onShuffleMeals: () -> Unit,
    onRefreshPlan: () -> Unit,
    onChatWithAssistant: () -> Unit,
    onViewHistory: () -> Unit,
    onOpenSettings: () -> Unit,
    onViewShoppingList: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MealGenius") },
                actions = {
                    IconButton(onClick = onOpenSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = true,
                    onClick = { /* Already on home screen */ }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Email, contentDescription = "Chat") },
                    label = { Text("Chat") },
                    selected = false,
                    onClick = onChatWithAssistant
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Shopping") },
                    label = { Text("Shopping") },
                    selected = false,
                    onClick = onViewShoppingList
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "History") },
                    label = { Text("History") },
                    selected = false,
                    onClick = onViewHistory
                )
            }
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
                text = "Your Weekly Meal Plan",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            // Placeholder for meal plan content
            Card(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Monday",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Breakfast: Avocado Toast with Eggs")
                    Text("Lunch: Chicken Caesar Salad")
                    Text("Dinner: Grilled Salmon with Vegetables")
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(
                        onClick = onViewMealPlanDetails,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("View Details")
                    }
                }
            }

            // Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = onShuffleMeals) {
                    Icon(Icons.Default.Refresh, contentDescription = "Shuffle")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Shuffle")
                }

                Button(onClick = onRefreshPlan) {
                    Icon(Icons.Default.Add, contentDescription = "New Plan")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("New Plan")
                }
            }
        }
    }
}
