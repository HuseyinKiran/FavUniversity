# FavUniversity

## 📸 Screenshots

<p align="center">
  <img src="images/splash.png" width="31%" style="margin-right: 2%;" />
  <img src="images/home1.png" width="31%" style="margin-right: 2%;" />
  <img src="images/home2.png" width="31%" />
</p>

<p align="center">
  <img src="images/search1.png" width="31%" style="margin-right: 2%;" />
  <img src="images/search2.png" width="31%" style="margin-right: 2%;" />
  <img src="images/favorites.png" width="31%" />
</p>

<p align="center">
  <img src="images/favorites2.png" width="31%" style="margin-right: 2%;" />
  <img src="images/website.png" width="31%" />
</p>

## 📄 Project Overview

FavUniversity lists the cities in Türkiye and the universities within each city.

- Tap a city to expand and see its universities.
- Tap a university to expand its section and view details such as phone, address, rector, and website.
- You can directly call the university or open its website.
- Universities can be added to or removed from favorites, and all favorites are listed on a dedicated Favorites screen.

## 📌 Tech Stack

- **Architecture (MVVM + Clean Architecture)** – Layered, maintainable structure with separate `presentation`, `data`, `domain`, `core`, and `di` packages.
- **Kotlin Coroutines (Flow & StateFlow)** – Asynchronous and reactive data handling
    - `Flow` for data streams from API and Room
    - `StateFlow` for exposing UI state from ViewModels
- **Room Database** – Local persistence layer for storing and managing favorite universities.
- **Retrofit** – HTTP client for fetching city and university data from the REST API.
- **RecyclerView + ListAdapter (DiffUtil)** – Efficient list rendering, item animations, and partial updates for cities and universities.
- **Navigation Component** – Single-activity, multi-fragment navigation with a type-safe back stack.
- **Hilt (Dependency Injection)** – Simplifies dependency management and provides scoped components across layers.
- **ViewBinding** – Type-safe access to XML views without `findViewById`.
