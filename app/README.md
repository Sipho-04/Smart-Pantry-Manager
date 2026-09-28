# Smart Pantry Manager

## Project Description

Smart Pantry Manager is an Android mobile application developed to help users manage the ingredients available in their pantry.

The application allows users to add, view, edit and delete pantry items while recording quantities and expiry dates. It also provides low-stock and expiry alerts and allows users to search for pantry items.

A key feature of the application is the Suggested Recipes function. The application suggests recipes only when all required ingredients are available in the pantry in the required quantities. Partial ingredient matches are not accepted.

## Main Features

- Add pantry items
- View pantry items
- Edit pantry items
- Delete pantry items
- Search pantry items
- Monitor low-stock items
- Monitor expired and soon-to-expire items
- View pantry alerts
- View a collection of preloaded recipes
- Suggest recipes based on available pantry ingredients
- View recipe ingredients and preparation instructions
- Configure the low-stock threshold in Settings
- Persistent pantry data storage
- Toolbar navigation

## Recipe Matching

The recipe matching system follows a strict matching rule.

A recipe is suggested only when:

1. Every required ingredient is available in the pantry.
2. The available quantity is equal to or greater than the required quantity.
3. Missing ingredients cause the recipe not to be suggested.
4. Insufficient quantities cause the recipe not to be suggested.

The application also normalizes common ingredient names, including singular and plural forms such as `egg` and `eggs`.

## Technologies Used

- Java
- Android Studio
- XML
- SQLite
- SQLiteOpenHelper
- RecyclerView
- Custom RecyclerView Adapters
- Android Intents
- SharedPreferences for application settings

## Database

The application uses an SQLite database named:

`SmartPantry.db`

The database contains:

### Pantry Items

Stores:

- Item ID
- Item name
- Quantity
- Expiry date

### Recipes

Stores:

- Recipe ID
- Recipe name
- Ingredients
- Preparation instructions

The application uses `SQLiteOpenHelper` to create and manage the database.

Pantry data remains available after the application is closed and reopened.

## Main Screens

The application includes the following screens:

- Main Screen
- View Pantry Items
- Add/Edit Pantry Item
- Low Stock Items
- Expiry Items
- Alerts
- Search Pantry
- Settings
- Suggested Recipes
- Recipe Collection
- Recipe Details

## Navigation

The application uses a toolbar menu for navigation. The toolbar provides access to:

- View Pantry
- Recipes
- Settings

The application also provides buttons on the Main Screen for accessing its main functions.

## How to Run the Project

1. Clone or download the repository.
2. Open the project in Android Studio.
3. Allow Android Studio to synchronize the Gradle files.
4. Connect an Android device or start an Android emulator.
5. Build and run the application.

## Project Structure

The project contains Java source files, XML layouts, database classes, model classes, RecyclerView adapters and Android resources.

Important classes include:

- `MainActivity`
- `DatabaseHelper`
- `PantryItem`
- `PantryAdapter`
- `Recipe`
- `RecipeAdapter`
- `SuggestedRecipesActivity`
- `RecipeDetailActivity`

## Repository

This project is maintained in a public GitHub repository.

**Repository:**  
https://github.com/Sipho-04/Smart-Pantry-Manager

## Author

Sipho Ndimande