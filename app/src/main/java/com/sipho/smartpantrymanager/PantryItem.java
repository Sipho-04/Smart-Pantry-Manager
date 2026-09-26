package com.sipho.smartpantrymanager;

public class PantryItem {

    private int id;
    private String itemName;
    private int quantity;
    private String expiryDate;

    public PantryItem(
            int id,
            String itemName,
            int quantity,
            String expiryDate) {

        this.id = id;
        this.itemName = itemName;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }

    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getExpiryDate() {
        return expiryDate;
    }
}