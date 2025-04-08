# Gift List Management Application

This is a **Gift List Management Application** designed to help users create, manage, and organize gift lists. The app is built using **Java** and **Kotlin** and leverages **Android Jetpack** components such as Room Database and Fragment Navigation.

## Features

- **User Management**: 
  - Add and manage users.
  - Authenticate users using passcodes.

- **Gift List Management**:
  - Create, view, and delete gift lists for individual users.

- **Gift List Item Management**:
  - Add, view, and delete items (with quantities) within specific gift lists.

- **Database Integration**:
  - Uses Room Database to persist user data, gift lists, and gift list items.

- **Fragment Navigation**:
  - Fragment-based architecture for seamless navigation between screens.

## Technologies Used

- **Languages**: Java
- **Database**: Room Database
- **Architecture**: Fragment-based navigation
- **Build System**: Gradle

## Project Structure

- `MainActivity.java`: The main entry point of the app, handling fragment navigation and database initialization.
- `fragments/`: Contains fragments for user management, gift list management, and item management.
- `models/`: Defines the data models (`User`, `GiftList`, `GiftListItem`).
- `AppDatabase.java`: Defines the Room database and DAOs for database operations.

## How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/kshitijnatu/Gift-List-Manager.git
