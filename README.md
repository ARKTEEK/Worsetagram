# Worsetagram
Worsetagram is my first android project, it was made as a college assignment, to try out Kotlin and Android features. It's a Instagram look-alike with less features. Worsetagram uses libraries, such as Dagger-Hilt, Jetpack Compose, Firebase Authentication, Firebase Storage and Firestore Database.
## Features
- User Authentication: Sign up, login, and logout functionalities.
- Feed: View, like, and comment on posts from users.
- Post Creation: Create new posts with images and captions.
- Profile: View and edit your profile details.
- Search: Search for Users you want.
- Direct Messaging: Send and receive direct messages with other users.
## Known issues/missing features
- Share and Notification functions aren't implemented.
- Authentication errors, like wrong password and so on, aren't shown to the user.
## Simple showcase
<video src="https://github.com/ARKTEEK/Worsetagram/assets/69106392/68ee4bca-9ef6-4653-a83d-dc5e3207e4e5"></video>
## Project Structure
```
|-- app
|   |-- src
|       |-- main
|           |-- java
|               |-- me.arkteek.worsetagram
|                   |-- common
|                       |-- constants
|                       |-- utilities
|                   |-- data
|                       |-- source
|                       |-- repository
|                   |-- di
|                   |-- domain
|                       |-- model
|                       |-- repository
|                   |-- ui
|                       |-- component
|                       |-- navigation
|                       |-- screen
|                       |-- theme
|                       |-- viewModel
|                   |-- MainActivity.kt
|                   |-- App.kt
```
## Project Structure Breakdown
- common
    - constants: Contains all constants.
    - utilities: Contains reusable classes and functions.
- data
    - source: Contains local and remote data sources like AuthResources.
    - repository: Implements repository interfaces defined in the domain layer.
- di (Dependency Injection)
    - Contains all the dependency injection modules and components.
- domain
    - model: Contains the data models or entities used throughout the app.
    - repository: Defines interfaces for data operations.
- ui
    - component: Contains reusable UI components.
    - navigation: Everything related to navigation between screens.
    - theme: Contains custom themes and styles.
    - screen: Contains individual screens or destinations in the app.
    - viewModel: Contains ViewModels for each screen or view.
## Code Style
Project uses [ktfmt](https://github.com/facebook/ktfmt), a program that formats Kotlin code, based on [google-java-format](https://github.com/google/google-java-format).
