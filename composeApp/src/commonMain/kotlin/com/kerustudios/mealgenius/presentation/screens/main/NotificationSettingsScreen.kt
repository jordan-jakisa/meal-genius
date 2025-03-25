package com.kerustudios.mealgenius.presentation.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Screen for managing notification settings
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationSettingsScreen(
    onBack: () -> Unit,
    onSaveSettings: () -> Unit
) {
    var reminderDay by remember { mutableStateOf("Sunday") }
    var reminderTime by remember { mutableStateOf("6:00 PM") }
    
    val dayOptions = listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    var expandedDayMenu by remember { mutableStateOf(false) }
    
    val timeOptions = listOf("6:00 AM", "8:00 AM", "12:00 PM", "3:00 PM", "6:00 PM", "9:00 PM")
    var expandedTimeMenu by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notification Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(onClick = onSaveSettings) {
                        Text("Save")
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
                text = "Notification Settings",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            // Day selection
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Reminder Day")
                
                Box {
                    OutlinedButton(onClick = { expandedDayMenu = true }) {
                        Text(reminderDay)
                    }
                    
                    DropdownMenu(
                        expanded = expandedDayMenu,
                        onDismissRequest = { expandedDayMenu = false }
                    ) {
                        dayOptions.forEach { day ->
                            DropdownMenuItem(
                                onClick = {
                                    reminderDay = day
                                    expandedDayMenu = false
                                }, text = {
                                Text(day)
                            }
                            )
                        }
                    }
                }
            }
            
            // Time selection
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Reminder Time")
                
                Box {
                    OutlinedButton(onClick = { expandedTimeMenu = true }) {
                        Text(reminderTime)
                    }
                    
                    DropdownMenu(
                        expanded = expandedTimeMenu,
                        onDismissRequest = { expandedTimeMenu = false }
                    ) {
                        timeOptions.forEach { time ->
                            DropdownMenuItem(
                                onClick = {
                                    reminderTime = time
                                    expandedTimeMenu = false
                                }, text = {
                                Text(time)
                            }
                            )
                        }
                    }
                }
            }
        }
    }
}