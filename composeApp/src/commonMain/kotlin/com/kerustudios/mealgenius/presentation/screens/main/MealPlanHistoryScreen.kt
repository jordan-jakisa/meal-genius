package com.kerustudios.mealgenius.presentation.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Screen displaying the history of meal plans
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealPlanHistoryScreen(
    onBack: () -> Unit,
    onReusePlan: (String) -> Unit
) {
    // Sample history data
    val historyItems = listOf(
        MealPlanHistoryItem(
            id = "1",
            date = "Week of May 1, 2023",
            description = "High protein, low carb meal plan"
        ),
        MealPlanHistoryItem(
            id = "2",
            date = "Week of April 24, 2023",
            description = "Vegetarian meal plan with Italian dishes"
        ),
        MealPlanHistoryItem(
            id = "3",
            date = "Week of April 17, 2023",
            description = "Quick and easy meal plan for busy week"
        ),
        MealPlanHistoryItem(
            id = "4",
            date = "Week of April 10, 2023",
            description = "Family-friendly meal plan with kid favorites"
        ),
        MealPlanHistoryItem(
            id = "5",
            date = "Week of April 3, 2023",
            description = "Budget-friendly meal plan with leftovers"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meal Plan History") },
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
                text = "Previous Meal Plans",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            if (historyItems.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No previous meal plans found",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(historyItems) { item ->
                        HistoryItemCard(
                            item = item,
                            onReusePlan = { onReusePlan(item.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoryItemCard(
    item: MealPlanHistoryItem,
    onReusePlan: () -> Unit
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = item.date,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onReusePlan) {
                    Text("Reuse Plan")
                }
            }
        }
    }
}

private data class MealPlanHistoryItem(
    val id: String,
    val date: String,
    val description: String
)
