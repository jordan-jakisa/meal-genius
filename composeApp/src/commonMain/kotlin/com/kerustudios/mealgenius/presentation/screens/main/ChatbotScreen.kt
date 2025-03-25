package com.kerustudios.mealgenius.presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

/**
 * Screen for chatting with the AI assistant
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatbotScreen(
    onBack: () -> Unit,
    onCustomizeMealPlan: () -> Unit,
    onAddNewDish: () -> Unit,
    onReceiveTips: () -> Unit
) {
    // Sample chat messages
    val initialMessages = listOf(
        ChatMessage(
            "Hello! I'm your MealGenius assistant. How can I help you today?",
            false
        )
    )

    var messages by remember { mutableStateOf(initialMessages) }
    var inputText by remember { mutableStateOf("") }
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat with Assistant") },
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
        ) {
            // Chat messages
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(messages) { message ->
                    ChatMessageItem(message)
                }
            }

            // Quick action chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SuggestionChip(
                    onClick = {
                        val userMessage = "I want to customize my meal plan"
                        messages = messages + ChatMessage(userMessage, true)
                        coroutineScope.launch {
                            scrollState.animateScrollToItem(messages.size - 1)
                            // Simulate assistant response
                            messages = messages + ChatMessage(
                                "I can help you customize your meal plan. What would you like to change?",
                                false
                            )
                            scrollState.animateScrollToItem(messages.size - 1)
                        }
                        onCustomizeMealPlan()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Customize Plan", maxLines = 1)
                }

                SuggestionChip(
                    onClick = {
                        val userMessage = "I want to add a new dish"
                        messages = messages + ChatMessage(userMessage, true)
                        coroutineScope.launch {
                            scrollState.animateScrollToItem(messages.size - 1)
                            // Simulate assistant response
                            messages = messages + ChatMessage(
                                "Great! Let's add a new dish to your profile. What dish would you like to add?",
                                false
                            )
                            scrollState.animateScrollToItem(messages.size - 1)
                        }
                        onAddNewDish()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Add New Dish", maxLines = 1)
                }

                SuggestionChip(
                    onClick = {
                        val userMessage = "I need dietary advice"
                        messages = messages + ChatMessage(userMessage, true)
                        coroutineScope.launch {
                            scrollState.animateScrollToItem(messages.size - 1)
                            // Simulate assistant response
                            messages = messages + ChatMessage(
                                "I'd be happy to provide dietary advice. What specific information are you looking for?",
                                false
                            )
                            scrollState.animateScrollToItem(messages.size - 1)
                        }
                        onReceiveTips()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Get Advice", maxLines = 1)
                }
            }

            // Input field
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(24.dp)),
                    placeholder = { Text("Type a message...") },
                    singleLine = true,
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            val userMessage = inputText
                            inputText = ""
                            messages = messages + ChatMessage(userMessage, true)
                            coroutineScope.launch {
                                scrollState.animateScrollToItem(messages.size - 1)
                                // Simulate assistant response
                                messages = messages + ChatMessage(
                                    "I'm processing your request: \"$userMessage\". This is a placeholder response.",
                                    false
                                )
                                scrollState.animateScrollToItem(messages.size - 1)
                            }
                        }
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        Icons.Default.Send,
                        contentDescription = "Send",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatMessageItem(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 300.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (message.isUser) 16.dp else 0.dp,
                        bottomEnd = if (message.isUser) 0.dp else 16.dp
                    )
                )
                .background(
                    if (message.isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                )
                .padding(12.dp)
        ) {
            Text(
                text = message.text,
                color = if (message.isUser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun SuggestionChip(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(32.dp),
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        content()
    }
}

private data class ChatMessage(
    val text: String,
    val isUser: Boolean
)
