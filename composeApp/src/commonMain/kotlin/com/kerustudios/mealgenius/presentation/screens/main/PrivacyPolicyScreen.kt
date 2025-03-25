package com.kerustudios.mealgenius.presentation.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Screen displaying the privacy policy
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy Policy") },
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Privacy Policy",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Last Updated: March 25, 2023",
                style = MaterialTheme.typography.labelSmall
            )
            
            PolicySection(
                title = "Introduction",
                content = "Welcome to MealGenius. We respect your privacy and are committed to protecting your personal data. This Privacy Policy will inform you about how we look after your personal data when you use our application and tell you about your privacy rights and how the law protects you."
            )
            
            PolicySection(
                title = "Data We Collect",
                content = "We collect and process the following information:\n\n" +
                        "• Personal Information: Name, email address, and profile information you provide.\n\n" +
                        "• Usage Data: Information about how you use our application.\n\n" +
                        "• Meal Preferences: Information about your dietary preferences, goals, and activity level.\n\n" +
                        "• Device Information: Information about your mobile device or computer."
            )
            
            PolicySection(
                title = "How We Use Your Data",
                content = "We use your data for the following purposes:\n\n" +
                        "• To provide and maintain our service\n\n" +
                        "• To personalize your experience\n\n" +
                        "• To improve our application\n\n" +
                        "• To communicate with you\n\n" +
                        "• To provide customer support\n\n" +
                        "• To detect, prevent and address technical issues"
            )
            
            PolicySection(
                title = "Data Sharing and Disclosure",
                content = "We may share your information with:\n\n" +
                        "• Service Providers: We may employ third-party companies to facilitate our service, provide the service on our behalf, or assist us in analyzing how our service is used.\n\n" +
                        "• Business Transfers: If we are involved in a merger, acquisition, or sale of assets, your data may be transferred.\n\n" +
                        "• Legal Requirements: We may disclose your information if required by law."
            )
            
            PolicySection(
                title = "Data Security",
                content = "We implement appropriate security measures to protect your personal data against accidental loss, unauthorized access, or disclosure. However, no method of transmission over the Internet or electronic storage is 100% secure."
            )
            
            PolicySection(
                title = "Your Rights",
                content = "Depending on your location, you may have the following rights:\n\n" +
                        "• Access: You can request copies of your personal data.\n\n" +
                        "• Rectification: You can request that we correct inaccurate data.\n\n" +
                        "• Erasure: You can request that we delete your data.\n\n" +
                        "• Restriction: You can request that we restrict the processing of your data.\n\n" +
                        "• Data Portability: You can request that we transfer your data to another organization."
            )
            
            PolicySection(
                title = "Changes to This Policy",
                content = "We may update our Privacy Policy from time to time. We will notify you of any changes by posting the new Privacy Policy on this page and updating the 'Last Updated' date."
            )
            
            PolicySection(
                title = "Contact Us",
                content = "If you have any questions about this Privacy Policy, please contact us at privacy@mealgenius.com."
            )
            
            // Add some space at the bottom for better scrolling experience
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun PolicySection(
    title: String,
    content: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall
        )
        
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}