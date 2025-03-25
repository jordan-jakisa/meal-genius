# MealGenius Requirements Document

## 1. App Overview

MealGenius is a cross-platform application designed to help users plan their meals based on their preferences, goals,
and activity levels. The app collects user information during onboarding and uses this data to generate personalized
weekly meal plans. Users can refresh their meal plans, shuffle meals, and interact with a chatbot to customize their
meal plans or add new dishes. The app is built using Kotlin Multiplatform and Compose Multiplatform, allowing it to run
on Android, iOS, and desktop platforms.

## 2. User Onboarding Process

The onboarding process collects essential information from users to personalize their meal planning experience:

### 2.1 Dishes Selection
- Users select dishes they typically prepare from a predefined list
  - Multiple selections are allowed
  - This information helps tailor meal suggestions to dishes the user is familiar with

### 2.2 Meal Planning Goals
- Users select their meal planning goals from the following options:
  - Save Time
  - Eat Healthier
  - Reduce Food Waste
  - Save Money
  - Learn New Recipes
  - Weight Management
  - Accommodate Dietary Restrictions
  - Multiple selections are allowed
  - Goals help prioritize certain aspects of meal planning

### 2.3 Activity Level
- Users select their activity level from the following options:
  - Sedentary (Little to no exercise)
  - Lightly Active (Light exercise 1-3 days/week)
  - Moderately Active (Moderate exercise 3-5 days/week)
  - Active (Hard exercise 6-7 days/week)
  - Very Active (Very hard exercise & physical job or training twice a day)
  - Only one selection is allowed
  - Activity level helps tailor caloric recommendations

### 2.4 Completion
- Confirmation screen indicating that preferences have been saved
  - Transition to the main app functionality

## 3. User Flow

The following diagram illustrates the user flow through the MealGenius application, showing how users navigate between different screens and functionalities:

### 3.1 Onboarding Flow
1. **Welcome Screen**
   - User is greeted with a welcome message
   - App explains that it will collect preferences to personalize the experience
   - User clicks "Get Started" to begin the onboarding process

   2. **Dishes Selection Screen**
      - User is presented with a list of common dishes
      - User selects multiple dishes they typically prepare
      - User clicks "Next" to proceed or "Back" to return to the Welcome screen

   3. **Goals Selection Screen**
      - User is presented with a list of meal planning goals
      - User selects multiple goals that align with their needs
      - User clicks "Next" to proceed or "Back" to return to the Dishes screen

   4. **Activity Level Selection Screen**
      - User is presented with activity level options
      - User selects a single option that best represents their activity level
      - User clicks "Next" to proceed or "Back" to return to the Goals screen

   5. **Completion Screen**
      - User is informed that their preferences have been saved
      - User clicks "Get Started" to enter the main application

### 3.2 Main Application Flow
1. **Home Screen**
   - Displays the current meal plan
   - Provides options to refresh, shuffle, or customize the meal plan
   - Offers access to the chatbot

   2. **Meal Plan Interaction**
      - User can view detailed information about each meal
      - User can shuffle meals to get different combinations
      - User can request a refresh of the entire meal plan or specific days

   3. **Chatbot Interaction**
      - User can open the chatbot interface from the main screen
      - User can ask questions, request customizations, or add new dishes
      - Chatbot provides responses and updates the meal plan accordingly

### 3.3 Navigation Patterns

- The app uses a bottom navigation bar for main sections (Home, Chat, Shopping, History)
    - State-based navigation for switching between main screens
    - Back button functionality for returning to previous screens
  - Modal dialogs for quick actions and confirmations
    - Navigation callbacks for handling user interactions

## 4. Meal Plan Generation

### 4.1 Meal Plan Generation

- The app provides a framework for generating personalized meal plans
    - Currently displays sample meal data in the UI
    - Prepared for future integration with AI models or other meal generation services
    - UI components in place for displaying and interacting with meal plans

### 4.2 Personalization Factors
- Generated meal plans will consider:
  - User's preferred dishes
  - Selected meal planning goals
  - Activity level
  - Any dietary restrictions or preferences

### 4.3 Meal Plan Structure
- Weekly meal plans with breakfast, lunch, and dinner for each day
  - Each meal should include:
    - Meal name
    - Ingredients list
    - Preparation instructions
    - Nutritional information (calories, macronutrients)
    - Preparation time
    - Difficulty level

## 5. Weekly Meal Plan Refresh

### 5.1 User-Initiated Refresh
- Users can request a new meal plan at any time
  - Option to refresh the entire week or specific days

### 5.2 Automatic Suggestions
- The app will prompt users to refresh their meal plan at the beginning of each week
  - Notification system to remind users about meal plan refresh

### 5.3 Historical Data
- Store previous meal plans for reference
  - Allow users to reuse favorite meal plans from the past

## 6. Meal Plan Shuffling

### 6.1 Shuffle Functionality
- Allow users to shuffle meals within their current meal plan
  - Options to shuffle:
    - All meals for the week
    - Meals for specific days
    - Specific meal types (breakfast, lunch, or dinner)

### 6.2 Constraints
- Maintain nutritional balance when shuffling
  - Consider user preferences and goals

## 7. Chatbot Implementation

### 7.1 Chatbot Interface

- The app implements a chat interface for user interaction
    - Message display with user and assistant messages visually distinguished
    - Text input field for typing messages
    - Send button for submitting messages
    - Auto-scrolling to show the latest messages

### 7.2 Quick Actions

- The chatbot provides quick action buttons for common requests
    - "Customize Plan" for meal plan customization
    - "Add New Dish" for adding new dishes
    - "Get Advice" for receiving dietary tips
    - These actions trigger simulated responses in the current implementation

### 7.3 Simulated Responses

- The current implementation uses simulated responses
    - Predefined messages based on user input
    - UI prepared for future integration with more advanced AI capabilities
    - Framework in place for handling more complex interactions

## 8. Technical Requirements

### 8.1 Architecture

- The app follows clean architecture principles with separation of concerns:
    - Presentation layer (UI components, screens, and ViewModels)
    - Domain layer (business logic and use cases)
    - Data layer (data models and repositories)
- The app uses the MVVM (Model-View-ViewModel) pattern for the presentation layer

### 8.2 Cross-Platform Support

- Implemented using Kotlin Multiplatform for code sharing
  - Support for Android, iOS, and desktop platforms
  - Uses Compose Multiplatform for UI development
  - Shared UI components and business logic across platforms

### 8.3 Data Storage
- Secure storage of user preferences
  - Caching of meal plans for offline access
  - Synchronization across devices (optional)

### 8.4 API Integration
- Secure integration with OpenAI API
  - Proper error handling and retry mechanisms
  - Rate limiting and quota management

### 8.5 Performance Considerations
- Optimize API calls to minimize latency
  - Efficient rendering of meal plans
  - Background processing for meal plan generation

## 9. User Experience Requirements

### 9.1 Intuitive Interface
- Clear navigation between different sections
  - Visual representation of meal plans
  - Easy access to chatbot functionality

### 9.2 Accessibility
- Support for screen readers
  - Adjustable text sizes
  - High contrast mode

### 9.3 Offline Functionality
- Access to previously generated meal plans when offline
  - Queue changes to be synchronized when online

### 9.4 Design System

- The app implements Material Design 3 for consistent UI components and theming
    - Uses Material 3 components such as TopAppBar, NavigationBar, Card, Button, etc.
    - Follows Material 3 component guidelines for UI elements
    - Implements consistent spacing and typography
    - Supports both light and dark themes (through MaterialTheme)

## 10. Security and Privacy

### 10.1 Data Protection
- Secure storage of user preferences
  - Compliance with data protection regulations
  - Transparent privacy policy

### 10.2 API Security
- Secure handling of API keys
  - Encryption of data in transit
  - Regular security audits

## 11. Future Enhancements

### 11.1 Social Features
- Sharing meal plans with friends or family
  - Community-contributed recipes

### 11.2 Integration with Shopping Services
- Generate shopping lists from meal plans
  - Integration with grocery delivery services

### 11.3 Advanced Analytics
- Track nutritional intake over time
  - Provide insights on eating habits and suggestions for improvement
