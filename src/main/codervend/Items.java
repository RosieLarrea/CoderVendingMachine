package com.codervend;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Items {

    private final String code;
    private final String name;
    private final BigDecimal price;
    private final String category;

    private int quantity;

    // Maximum stock per item
    private static final int MAX_QUANTITY = 5;

    public Items(String code, String name, double price, String category) {
        this.code = code;
        this.name = name;
        this.price = BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP);
        this.category = category;
        this.quantity = MAX_QUANTITY;
    }

    // Getters
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    // Decrease stock after purchase
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Restock item back to max
    public void restock() {
        this.quantity = MAX_QUANTITY;
    }

    // Display item in inventory
    public String display() {
        if (quantity == 0) {
            return String.format("%s | %s | SOLD OUT", code, name);
        }

        return String.format(
                "%s | %s | %.2f CR | Stock: %d",
                code,
                name,
                price,
                quantity
        );
    }
}
