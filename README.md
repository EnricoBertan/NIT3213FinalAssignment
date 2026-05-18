# NIT3213 Final Assignment
### Android Application Development — Victoria University Sydney

![Kotlin](https://img.shields.io/badge/Kotlin-1.9.24-purple?logo=kotlin)
![Android](https://img.shields.io/badge/Android-API%2024+-green?logo=android)
![Hilt](https://img.shields.io/badge/DI-Hilt-orange)
![Architecture](https://img.shields.io/badge/Architecture-MVVM%20Clean-blue)

**Student:** Enrico Bertan Valerio Silva  
**Student ID:** s8164431  
**Class:** Sydney  
**Lecturer:** Hammad Mujtaba  

---

## About
A modern Android application built for the NIT3213 Mobile Application Development final assignment. Features a **Neon Noir dark theme** with purple and pink accents, demonstrating:
- REST API integration with Retrofit
- MVVM + Clean Architecture
- Dependency Injection with Hilt
- Kotlin Coroutines + StateFlow
- Unit Testing with MockK

---

## Screenshots

| Login | Dashboard | Details |
|---|---|---|
| Dark themed login with student credentials | RecyclerView with shimmer loading and search | Full entity details with description |

---

## App Flow
User opens app
↓
Login Screen — POST /sydney/auth
↓ (keypass received)
Dashboard Screen — GET /dashboard/{keypass}
↓ (entity selected)
Details Screen — displays full entity info
---

## Architecture

This app follows **Clean Architecture** with 3 distinct layers:

**UI Layer** → LoginFragment, DashboardFragment, DetailsFragment, ViewModels

**Domain Layer** → LoginUseCase, GetDashboardUseCase, Repository Interfaces

**Data Layer** → AuthRepositoryImpl, DashboardRepositoryImpl, ApiService, Hilt Modules

The data flows like this:

**Fragment → ViewModel → UseCase → Repository → ApiService → Server**
---

## Features

### Login Screen
- Student ID and First Name authentication
- POST request to `/sydney/auth`
- Error handling for invalid credentials
- Loading state with progress indicator
- Neon Noir dark UI with purple accents

### Dashboard Screen
- RecyclerView with **shimmer loading effect**
- **Real-time search/filter** bar
- Entity count display
- **Card press animation** on item selection
- Navigates to Details on item click

### Details Screen
- Displays all entity properties
- Full description in a separate card
- Clean dark card layout

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin 1.9.24 |
| Architecture | MVVM + Clean Architecture |
| Dependency Injection | Hilt 2.51.1 |
| Networking | Retrofit 2.11.0 + OkHttp 4.12.0 |
| Async | Kotlin Coroutines + StateFlow |
| Navigation | Jetpack Navigation Component 2.7.7 |
| UI | Material 3 + Neon Noir Dark Theme |
| Loading Effect | Facebook Shimmer 0.5.0 |
| Testing | JUnit4 + MockK 1.13.11 |

---

## Project Structure

**data/**
- model → LoginRequest, LoginResponse, DashboardResponse
- network → ApiService, RetrofitInstance
- repository → AuthRepositoryImpl, DashboardRepositoryImpl

**domain/**
- repository → AuthRepository, DashboardRepository (interfaces)
- usecase → LoginUseCase, GetDashboardUseCase

**ui/**
- login → LoginFragment, LoginViewModel
- dashboard → DashboardFragment, DashboardViewModel, EntityAdapter
- details → DetailsFragment, DetailsViewModel

**di/** → NetworkModule, RepositoryModule
---

## Dependency Injection

This app uses **Hilt** for dependency injection with the following setup:

```kotlin
// Application class
@HiltAndroidApp
class MyApplication : Application()

// Fragments and Activities
@AndroidEntryPoint
class LoginFragment : Fragment()

// ViewModels
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel()

// Modules
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService
}
```

---

## API Details

- **Base URL:** `https://nit3213api.onrender.com/`
- **Login:** `POST /sydney/auth`
- **Dashboard:** `GET /dashboard/{keypass}`

### Login Request
```json
{
    "username": "s1234567",
    "password": "FirstName"
}
```

### Login Response
```json
{
    "keypass": "history"
}
```

### Dashboard Response
```json
{
    "entities": [
        {
            "event": "World War II",
            "startYear": 1939,
            "endYear": 1945,
            "location": "Global",
            "keyFigure": "Winston Churchill",
            "description": "A global war..."
        }
    ],
    "entityTotal": 7
}
```

---

## How to Build and Run

### Prerequisites
- Android Studio Hedgehog or later
- JDK 17+
- Android SDK API 24+
- Internet connection

### Steps
1. Clone the repository:
```bash
git clone https://github.com/EnricoBertan/NIT3213FinalAssignment.git
```
2. Open in Android Studio
3. Wait for Gradle sync to complete
4. Run on emulator or physical device (API 24+)

### Login Credentials
- **Username:** Your Student ID (e.g. `s8164431`)
- **Password:** Your First Name (e.g. `Enrico Bertan`)

---

## Running Tests

Right click on the `test` folder → **Run Tests**

Or via terminal:
```bash
./gradlew test
```

### Test Coverage
- `LoginViewModelTest` — 4 tests
  - Empty username returns error state
  - Empty password returns error state
  - Successful login returns keypass
  - Failed login returns error state
- `DashboardViewModelTest` — 3 tests
  - Initial state is loading
  - Successful load returns entities
  - Failed load returns error state

---

## Git Commit History

| # | Commit Message |
|---|---|
| 1 | `Initial project setup` |
| 2 | `Add data models, API service, repository layer, and Hilt DI modules` |
| 3 | `Complete NIT3213 Final Assignment` |
| 4 | `Add use cases to domain layer and update ViewModels and unit tests` |
| 5 | `Add Neon Noir dark theme and color palette` |
| 6 | `Redesign Login screen with Neon Noir UI` |
| 7 | `Add card press animation and ripple effect to RecyclerView items` |
| 8 | `Add comprehensive README with setup instructions and project overview` |
| 9 | `Fix entity display with Map<String, Any> and update RecyclerView card layout` |
| 10 | `Merge branch 'main'` |
| 11 | `Update unit tests to use Map<String, Any> for entity data` |
| 12 | `Improve README with architecture diagram, badges and API examples` |
| 13 | `Fix README architecture and project structure formatting` |
| 14 | `Final submission - fully working app with Neon Noir theme` |
