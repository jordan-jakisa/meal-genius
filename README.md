# MealGenius

MealGenius is a cross-platform meal planning application built with Kotlin Multiplatform and Compose Multiplatform. It
helps users plan their meals based on their preferences, goals, and activity levels.

## Features

- **Personalized Meal Planning**: Generate meal plans based on user preferences
- **Interactive Chatbot**: Chat with an assistant to customize meal plans and get dietary advice
- **Shopping List**: Manage ingredients needed for your meal plans
- **Meal Plan History**: View and reuse previous meal plans

## Technology Stack

- **Kotlin Multiplatform**: For sharing code across different platforms
- **Compose Multiplatform**: For building the user interface
- **Clean Architecture**: Separation of concerns with presentation, domain, and data layers
- **MVVM Pattern**: For the presentation layer

## Supported Platforms

- Android
- iOS
- Desktop (JVM)

## Getting Started

1. Clone the repository
2. Open the project in Android Studio or IntelliJ IDEA
3. Build and run on your desired platform

## Project Structure

```
com.kerustudios.mealgenius/
├── data/
│   ├── models/       # Data models
│   └── repositories/ # Data repositories
├── domain/
│   ├── repository/   # Repository interfaces
│   └── usecases/     # Business logic use cases
└── presentation/
    ├── navigation/   # Navigation components
    ├── screens/      # UI screens
    │   ├── main/     # Main app screens
    │   └── onboarding/ # Onboarding screens
    └── viewmodels/   # ViewModels for UI state management
```

## Development Guidelines

1. Follow the clean architecture principles
2. Use Compose for UI development
3. Ensure code is shared across platforms when possible
4. Follow Kotlin coding conventions
5. Use Material Design 3 for UI components and theming
