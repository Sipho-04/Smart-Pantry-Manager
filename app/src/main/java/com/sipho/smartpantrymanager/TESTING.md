# Smart Pantry Manager - Testing

## 1. Pantry CRUD Testing

The pantry management functions were tested to confirm that users can:

- Add a new pantry item.
- View existing pantry items.
- Edit an existing pantry item.
- Delete a pantry item.

The CRUD operations were tested using SQLite database storage.

## 2. Persistence Testing

The application was closed and reopened after pantry items were added.

The stored pantry records remained available after reopening the application, confirming that the data is persisted in the SQLite database.

## 3. Low Stock Testing

The Low Stock Items screen was tested using different pantry quantities.

Items with quantities at or below the configured low-stock threshold were displayed.

## 4. Expiry Testing

The Expiry Items screen was tested using pantry items with different expiry dates.

Expired items and items approaching their expiry date were identified by the application.

## 5. Alerts Testing

The Alerts screen was tested to confirm that low-stock and expiry-related alerts are displayed using the pantry data stored in SQLite.

## 6. Search Testing

The Search Pantry function was tested using pantry item names.

The application successfully displayed matching pantry records based on the search text.

## 7. Recipe Collection Testing

The Recipe Collection screen was tested and the preloaded recipe collection was displayed.

Individual recipes were opened to verify that their ingredients and preparation instructions were available.

## 8. Strict Recipe Matching Testing

The Suggested Recipes feature was tested with different pantry combinations.

A recipe was displayed only when all required ingredients were present in the pantry in sufficient quantities.

When an ingredient was missing or the available quantity was insufficient, the recipe was not displayed.

The feature was also tested by adding and removing ingredients to confirm that the suggestions changed accordingly.

## 9. No-Match Testing

The Suggested Recipes screen was tested with insufficient pantry ingredients.

When no recipe could be prepared using the current pantry, the application displayed a clear no-match message instead of leaving the screen blank.