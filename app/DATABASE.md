# Smart Pantry Manager Database

## Database Details

The Smart Pantry Manager application uses SQLite for persistent data storage.

Database name:

`SmartPantry.db`

Database version:

`5`

The database is managed using Android's `SQLiteOpenHelper`.

## Database Tables

The application currently uses two main database tables:

1. `pantry_items`
2. `recipes`

---

## 1. pantry_items

The `pantry_items` table stores the ingredients currently available in the user's pantry.

| Column | Data Type | Description |
|---|---|---|
| `id` | INTEGER | Unique identifier for each pantry item |
| `item_name` | TEXT | Name of the pantry ingredient |
| `quantity` | INTEGER | Quantity currently available |
| `expiry_date` | TEXT | Expiry date of the pantry item |

### Primary Key

`id` is the primary key and uses:

`AUTOINCREMENT`

### Constraints

- `item_name` cannot be NULL.
- `quantity` cannot be NULL.
- `expiry_date` may be NULL.

---

## 2. recipes

The `recipes` table stores the preloaded recipes used by the application.

| Column | Data Type | Description |
|---|---|---|
| `id` | INTEGER | Unique identifier for each recipe |
| `recipe_name` | TEXT | Name of the recipe |
| `ingredients` | TEXT | Required ingredients and quantities |
| `preparation` | TEXT | Recipe preparation instructions |

### Primary Key

`id` is the primary key and uses:

`AUTOINCREMENT`

### Constraints

- `recipe_name` cannot be NULL.
- `ingredients` cannot be NULL.
- `preparation` cannot be NULL.

---

## Database Operations

The `DatabaseHelper` class provides database operations for pantry items and recipes.

### Pantry CRUD

The application supports:

- Create pantry item
- Read pantry items
- Update pantry item
- Delete pantry item

These operations are used by the pantry management screens.

### Recipe Operations

The application supports:

- Adding recipes
- Reading recipes
- Loading the default recipe collection

The application contains 20 preloaded recipes.

---

## Recipe Data Format

Recipe ingredients are stored as text using the following format:

`ingredient:quantity`

Multiple ingredients are separated by commas.

Example:

`bread:2,egg:2`

This represents a recipe requiring:

- Bread: 2
- Egg: 2

---

## Database Versioning

The database currently uses version `5`.

`onUpgrade()` is used to handle database changes between versions.

The application also reseeds the default recipe collection when required by the database upgrade logic.

---

## Persistence

Pantry items are stored in SQLite and remain available after the application is closed and reopened.

This allows the application to maintain the user's pantry information between sessions.