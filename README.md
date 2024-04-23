# Worsetagram

Worsetagram is a Instagram clone built with Jetpack Compose and Kotlin, designed specifically for Android devices. As the name suggests, it's a simplified, worse version of Instagram, focusing on core functionalities without all the bells and whistles.


## Features

- User Authentication: Sign up, login, and logout functionalities.
- Feed: View, like, and comment on posts from users.
- Post Creation: Create and publish new posts with images and captions.
- Profile: View and edit your profile details.
- Search: Search for Users or Posts you want.
- Direct Messaging: Send and receive direct messages with other users.

## Project Structure
```
|-- app
|   |-- src
|       |-- main
|           |-- java
|               |-- me.arkteek.worsetagram
|                   |-- di
|                   |-- domain
|                       |-- model
|                       |-- repository
|                       |-- usecase
|                   |-- data
|                       |-- local
|                       |-- remote
|                       |-- repository
|                   |-- ui
|                       |-- theme
|                       |-- view
|                           |-- component
|                           |-- screen
|                       |-- viewModel
|                   |-- MainActivity.kt
```
## Project Structure Breakdown
- di (Dependency Injection)
    - Contains all the dependency injection modules and components.
- domain
    - model: Contains the data models or entities used throughout the app.
    - repository: Defines interfaces for data operations.
    - usecase: Contains business logic or use cases.
- data
    - local: Contains local data sources like Room databases, shared preferences, etc.
    - remote: Contains remote data sources like API clients.
    - repository: Implements repository interfaces defined in the domain layer.
- ui
    - theme: Contains custom themes and styles.
    - view
        - component: Contains reusable UI components.
        - screen: Contains individual screens or destinations in the app.
    - viewModel: Contains ViewModels for each screen or view.
## Code Style

Project uses [ktfmt](https://github.com/facebook/ktfmt), a program that formats Kotlin code, based on [google-java-format](https://github.com/google/google-java-format).
