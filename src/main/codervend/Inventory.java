package com.codervend;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private final Map<String, Items> items = new HashMap<>();

    public Inventory() {
        items.put("A1", new Items("A1", "NullPointer Snack", 2.75, "Debug"));
        items.put("A2", new Items("A2", "Stack Overflow Chips", 3.05, "Debug"));
        items.put("A3", new Items("A3", "Rubber Duck Bites", 2.50, "Debug"));
        items.put("A4", new Items("A4", "Merge Conflict Pretzels", 2.65, "Debug"));

        items.put("B1", new Items("B1", "Refactor Ramen", 3.35, "Compile"));
        items.put("B2", new Items("B2", "JUnit Jelly Beans", 2.20, "Compile"));
        items.put("B3", new Items("B3", "Spring Boot Soda", 2.85, "Compile"));
        items.put("B4", new Items("B4", "Lambda Lemon Drops", 2.10, "Compile"));

        items.put("C1", new Items("C1", "Coffee++", 2.25, "Coffee"));
        items.put("C2", new Items("C2", "Dark Mode Drip", 2.75, "Coffee"));
        items.put("C3", new Items("C3", "Ctrl+Z Cola", 2.60, "Coffee"));
        items.put("C4", new Items("C4", "API Key Energy", 2.95, "Coffee"));

        items.put("D1", new Items("D1", "Mechanical Switch Popcorn", 3.45, "Keyboard"));
        items.put("D2", new Items("D2", "Sticky Keys Gummies", 1.95, "Keyboard"));
        items.put("D3", new Items("D3", "Spacebar Snacks", 1.75, "Keyboard"));
        items.put("D4", new Items("D4", "Ctrl+C Ctrl+V Mints", 0.95, "Keyboard"));
    }

    public void displayInventory() {
        System.out.println("\n=== DEV-O-MATIC INVENTORY ===");
        for (Items item : items.values()) {
            System.out.println(item.display());
        }
    }

    public Items getItem(String code) {
        return items.get(code);
    }

    // Used for dev report / sales tracking
    public Map<String, Items> getAllItems() {
        return items;
    }
}
