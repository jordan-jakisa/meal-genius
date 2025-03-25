package com.kerustudios.mealgenius.presentation.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Screen displaying dietary advice and tips
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiveTipsScreen(
    onBack: () -> Unit
) {
    // Sample tips data
    val tips = listOf(
        DietaryTip(
            title = "Balanced Nutrition",
            description = "Aim for a balanced plate with 1/2 vegetables, 1/4 protein, and 1/4 whole grains for optimal nutrition.",
            category = "Nutrition"
        ),
        DietaryTip(
            title = "Protein Timing",
            description = "Consume protein within 30 minutes after exercise to help with muscle recovery and growth.",
            category = "Fitness"
        ),
        DietaryTip(
            title = "Hydration",
            description = "Drink at least 8 glasses of water daily. Staying hydrated improves energy levels and helps control hunger.",
            category = "Wellness"
        ),
        DietaryTip(
            title = "Meal Prep",
            description = "Spend 2-3 hours on weekends preparing meals for the week to save time and ensure healthier eating.",
            category = "Planning"
        ),
        DietaryTip(
            title = "Portion Control",
            description = "Use smaller plates to help control portion sizes. This simple trick can reduce calorie intake by 20-30%.",
            category = "Nutrition"
        ),
        DietaryTip(
            title = "Mindful Eating",
            description = "Eat slowly and without distractions. It takes about 20 minutes for your brain to register that you're full.",
            category = "Wellness"
        ),
        DietaryTip(
            title = "Healthy Snacking",
            description = "Keep healthy snacks like nuts, fruits, and yogurt readily available to avoid reaching for processed foods.",
            category = "Nutrition"
        ),
        DietaryTip(
            title = "Fiber Intake",
            description = "Include high-fiber foods in your diet to improve digestion and help maintain a healthy weight.",
            category = "Nutrition"
        ),
        DietaryTip(
            title = "Meal Timing",
            description = "Try to eat your last meal at least 2-3 hours before bedtime for better sleep and digestion.",
            category = "Planning"
        ),
        DietaryTip(
            title = "Balanced Macros",
            description = "For active individuals, aim for a macronutrient ratio of approximately 40% carbs, 30% protein, and 30% fat.",
            category = "Fitness"
        )
    )

    // Group tips by category
    val groupedTips = tips.groupBy { it.category }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dietary Tips") },
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
                text = "Personalized Dietary Advice",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            // Tips list grouped by category
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                groupedTips.forEach { (category, tipsInCategory) ->
                    item {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    items(tipsInCategory) { tip ->
                        TipCard(tip = tip)
                    }

                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            // Note about personalization
            Card(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "These tips are personalized based on your goals and preferences. For more specific advice, chat with our assistant.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun TipCard(
    tip: DietaryTip
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
                text = tip.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleSmall
            )

            Text(
                text = tip.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

private data class DietaryTip(
    val title: String,
    val description: String,
    val category: String
)
