package com.kerustudios.mealgenius.presentation.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kerustudios.mealgenius.presentation.navigation.Destinations

/**
 * Screen displaying the shopping list
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(
    navController: NavController
) {
    val onBack: () -> Unit = { navController.navigateUp() }
    val onExportList: () -> Unit = {}

    // Sample shopping list data grouped by category
    val categories = listOf(
        ShoppingCategory(
            name = "Produce",
            items = listOf(
                ShoppingItem("Avocado", "2 medium"),
                ShoppingItem("Spinach", "1 bag"),
                ShoppingItem("Tomatoes", "4 medium"),
                ShoppingItem("Bell Peppers", "2 red")
            )
        ),
        ShoppingCategory(
            name = "Dairy",
            items = listOf(
                ShoppingItem("Eggs", "1 dozen"),
                ShoppingItem("Greek Yogurt", "16 oz"),
                ShoppingItem("Cheddar Cheese", "8 oz")
            )
        ),
        ShoppingCategory(
            name = "Meat & Seafood",
            items = listOf(
                ShoppingItem("Chicken Breast", "2 lbs"),
                ShoppingItem("Salmon Fillets", "1 lb"),
                ShoppingItem("Ground Turkey", "1 lb")
            )
        ),
        ShoppingCategory(
            name = "Grains & Pasta",
            items = listOf(
                ShoppingItem("Brown Rice", "2 cups"),
                ShoppingItem("Whole Wheat Bread", "1 loaf"),
                ShoppingItem("Quinoa", "1 cup")
            )
        ),
        ShoppingCategory(
            name = "Canned & Packaged",
            items = listOf(
                ShoppingItem("Black Beans", "1 can"),
                ShoppingItem("Diced Tomatoes", "2 cans"),
                ShoppingItem("Chicken Broth", "32 oz")
            )
        )
    )

    // Track checked items
    val checkedItems = remember { mutableStateMapOf<String, Boolean>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shopping List") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onExportList) {
                        Icon(Icons.Default.Share, contentDescription = "Export List")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = false,
                    onClick = { navController.navigate(Destinations.Home.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Email, contentDescription = "Chat") },
                    label = { Text("Chat") },
                    selected = false,
                    onClick = { navController.navigate(Destinations.Chatbot.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Shopping") },
                    label = { Text("Shopping") },
                    selected = true,
                    onClick = { navController.navigate(Destinations.ShoppingList.route) }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "History") },
                    label = { Text("History") },
                    selected = false,
                    onClick = { navController.navigate(Destinations.MealPlanHistory.route) }
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
            // Header with count of checked items
            val totalItems = categories.sumOf { it.items.size }
            val checkedCount = checkedItems.values.count { it }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Shopping List",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "$checkedCount/$totalItems items checked",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            // Shopping list
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(categories) { category ->
                    ShoppingCategorySection(
                        category = category,
                        checkedItems = checkedItems
                    )
                }
            }

            // Clear checked items button
            if (checkedCount > 0) {
                Button(
                    onClick = { 
                        checkedItems.keys.forEach { key ->
                            if (checkedItems[key] == true) {
                                checkedItems[key] = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Clear Checked Items")
                }
            }
        }
    }
}

@Composable
private fun ShoppingCategorySection(
    category: ShoppingCategory,
    checkedItems: MutableMap<String, Boolean>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = category.name,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                category.items.forEach { item ->
                    ShoppingItemRow(
                        item = item,
                        isChecked = checkedItems["${category.name}:${item.name}"] == true,
                        onCheckedChange = { isChecked ->
                            checkedItems["${category.name}:${item.name}"] = isChecked
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ShoppingItemRow(
    item: ShoppingItem,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None
            )
            Text(
                text = item.quantity,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None
            )
        }
    }
}

private data class ShoppingCategory(
    val name: String,
    val items: List<ShoppingItem>
)

private data class ShoppingItem(
    val name: String,
    val quantity: String
)
