# MealGenius Project Overview

## Project Description
MealGenius is a cross-platform application developed by Keru Studios that aims to provide meal-related functionality. While the project is in its early stages of development, it is designed to run on multiple platforms including Android, iOS, and desktop.

## Technology Stack
- **Kotlin Multiplatform**: For sharing code across different platforms
- **Compose Multiplatform**: For building the user interface
- **Clean Architecture**: The project follows a clean architecture approach with:
  - **Presentation Layer**: UI components and ViewModels
  - **Domain Layer**: Business logic and use cases
  - **Data Layer**: Data models and repositories

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
    ├── screens/      # UI screens
    └── viewmodels/   # ViewModels for UI state management
```

## Build System
The project uses Gradle with the Kotlin DSL for build configuration. It targets:
- Android
- iOS (iosX64, iosArm64, iosSimulatorArm64)
- Desktop (JVM)

## Development Guidelines
1. Follow the clean architecture principles
2. Use Compose for UI development
3. Ensure code is shared across platforms when possible
4. Write unit tests for business logic
5. Follow Kotlin coding conventions
6. Use Material Design 3 for UI components and theming

## Getting Started
1. Clone the repository
2. Open the project in Android Studio or IntelliJ IDEA
3. Build and run on your desired platform
