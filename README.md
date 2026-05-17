# NIT3213 Final Assignment
### Android Application Development — Victoria University Sydney

**Student:** Enrico Bertan Valerio Silva  
**Student ID:** s8164431  
**Class:** Sydney  
**Lecturer:** Hammad Mujtaba  

---

## About
A modern Android application built for the NIT3213 Mobile Application Development final assignment. The app features a Neon Noir dark theme with purple and pink accents, demonstrating API integration, MVVM Clean Architecture, Dependency Injection with Hilt, and unit testing.

---

## Screens

### 1. Login Screen
- Student ID and First Name authentication
- POST request to `/sydney/auth`
- Error handling for invalid credentials
- Loading state with progress indicator

### 2. Dashboard Screen
- RecyclerView displaying entities from the API
- Shimmer loading effect while fetching data
- Search/filter bar to filter entities in real time
- Entity count display
- Card press animation on item selection
- Navigates to Details on item click

### 3. Details Screen
- Displays all entity properties
- Full description in a separate card
- Clean dark card layout with purple and pink accents

---

## Tech Stack
| Layer | Technology |
|---|---|
| Language | Kotlin |
| Architecture | MVVM + Clean Architecture |
| Dependency Injection | Hilt |
| Networking | Retrofit + OkHttp + Gson |
| Async | Kotlin Coroutines + StateFlow |
| Navigation | Jetpack Navigation Component |
| UI | Material 3 + Neon Noir Dark Theme |
| Loading Effect | Facebook Shimmer |
| Testing | JUnit4 + MockK + Coroutines Test |

---

## Project Structure
com.enrico.nit3213
├── data
│   ├── model           # LoginRequest, LoginResponse, DashboardResponse
│   ├── network         # ApiService, RetrofitInstance
│   └── repository      # AuthRepositoryImpl, DashboardRepositoryImpl
├── domain
│   ├── repository      # AuthRepository, DashboardRepository (interfaces)
│   └── usecase         # LoginUseCase, GetDashboardUseCase
├── ui
│   ├── login           # LoginFragment, LoginViewModel
│   ├── dashboard       # DashboardFragment, DashboardViewModel, EntityAdapter
│   └── details         # DetailsFragment, DetailsViewModel
└── di                  # NetworkModule, RepositoryModule (Hilt)
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
    "keypass": "topicName"
}
```

### Dashboard Response
```json
{
    "entities": [
        {
            "property1": "value1",
            "property2": "value2",
            "description": "Detailed description"
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
- **Password:** Your First Name (e.g. `Enrico`)

---

## Running Tests
Right click on the `test` folder → **Run Tests**

Or via terminal:
```bash
./gradlew test
```

### Test Coverage
- `LoginViewModelTest` — 4 tests covering idle, loading, success and error states
- `DashboardViewModelTest` — 3 tests covering loading, success and error states

---

## Git Commit History
This project follows a structured commit history demonstrating incremental development:

1. `Initial project setup`
2. `Add data models, API service, repository layer, and Hilt DI modules`
3. `Complete NIT3213 Final Assignment`
4. `Add use cases to domain layer and update ViewModels and unit tests`
5. `Add Neon Noir dark theme and color palette`
6. `Redesign Login screen with Neon Noir UI`
7. `Add card press animation and ripple effect to RecyclerView items`

---

## Dependencies
```toml
retrofit = "2.11.0"
okhttp = "4.12.0"
hilt = "2.51.1"
coroutines = "1.8.1"
lifecycle = "2.8.2"
navigation = "2.7.7"
shimmer = "0.5.0"
mockk = "1.13.11"
turbine = "1.1.0"
```
