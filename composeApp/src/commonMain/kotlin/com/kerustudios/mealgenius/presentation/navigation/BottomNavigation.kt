package com.kerustudios.mealgenius.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.kerustudios.mealgenius.presentation.screens.main.ChatbotScreen
import com.kerustudios.mealgenius.presentation.screens.main.HomeScreen
import com.kerustudios.mealgenius.presentation.screens.main.MealPlanHistoryScreen
import com.kerustudios.mealgenius.presentation.screens.main.ShoppingListScreen

/**
 * Main navigation with bottom navigation bar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTabNavigatorWithBottomBar() {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Email, contentDescription = "Chat") },
                    label = { Text("Chat") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Shopping") },
                    label = { Text("Shopping") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "History") },
                    label = { Text("History") },
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 }
                )
            }
        }
    ) { paddingValues ->
        when (selectedTab) {
            0 -> HomeScreen(
                onViewMealPlanDetails = { /* Navigate to meal plan details */ },
                onShuffleMeals = { /* Navigate to shuffle meals */ },
                onRefreshPlan = { /* Navigate to refresh plan */ },
                onChatWithAssistant = { selectedTab = 1 },
                onViewHistory = { selectedTab = 3 },
                onOpenSettings = { /* Navigate to settings */ },
                onViewShoppingList = { selectedTab = 2 }
            )
            1 -> ChatbotScreen(
                onBack = { selectedTab = 0 },
                onCustomizeMealPlan = { /* Navigate to customize meal plan */ },
                onAddNewDish = { /* Navigate to add new dish */ },
                onReceiveTips = { /* Navigate to receive tips */ }
            )
            2 -> ShoppingListScreen(
                onBack = { selectedTab = 0 },
                onExportList = { /* Handle export list */ }
            )
            3 -> MealPlanHistoryScreen(
                onBack = { selectedTab = 0 },
                onReusePlan = { planId -> /* Handle reuse plan */ }
            )
        }
    }
}
