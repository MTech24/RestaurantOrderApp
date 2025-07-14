# 🍽️ Restaurant Order App

This is a simple **Restaurant Order App** built with **Kotlin** and **Jetpack Compose**, developed as part of a **Coursera course** on Android app development.

It demonstrates how to build a modern Android app using:
- declarative UI with Compose,
- persistent local storage with Room,
- network data fetching with Ktor,
- and JSON parsing with Gson.

---

## 📱 Features

- **Profile Screen**:
  - Input and save **First Name**, **Last Name**, and **Email**.
  - Stored using `SharedPreferences`.

- **Home Screen**:
  - Loads and displays a list of **menu items**.
  - Items include a **title**, **description**, **price**, and **image**.
  - **Search bar** to filter items by title.
  - **Category filter** (e.g., All, Starters, Mains, Desserts, Drinks).
  - **Sorting** options (e.g., by price or title).
  - **Bottom sheet** with more details on selected item.

---

## 🛠 Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Navigation**: `androidx.navigation:navigation-compose`
- **Networking**: Ktor Client (CIO engine)
- **JSON Parsing**: Gson
- **Local Database**: Room
- **State Management**: `remember`, `mutableStateOf`, `derivedStateOf`

---

## 📄 License
MIT License. Use freely for learning, experimenting, or improving.