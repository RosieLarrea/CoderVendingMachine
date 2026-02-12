package com.codervend;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Application {

    private static final Map<String, Integer> salesReport = new HashMap<>();
    private static BigDecimal totalSales = BigDecimal.ZERO;

    public static void main(String[] args) {
        com.codervend.Inventory inventory = new com.codervend.Inventory();
        Scanner scanner = new Scanner(System.in);
        BigDecimal credits = BigDecimal.ZERO;

        while (true) {
            System.out.println("\n=== DEV-O-MATIC VENDING TERMINAL v1.0 ===");
            System.out.println("1. List Inventory");
            System.out.println("2. Purchase Mode");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                inventory.displayInventory();
            } else if (choice.equals("2")) {
                credits = runTransaction(scanner, inventory, credits);
            } else if (choice.equals("3")) {
                System.out.println("Session ended. May your builds be green. ✅");
                break;
            } else if (choice.equalsIgnoreCase("dev")) {
                // hidden dev command
                generateDevReport(inventory, salesReport, totalSales);
            } else {
                System.out.println("ERROR: Invalid option. Try 1, 2, or 3.");
            }
        }

        scanner.close();
    }

    private static BigDecimal runTransaction(Scanner scanner, Inventory inventory, BigDecimal credits) {
        boolean transaction = true;

        while (transaction) {
            System.out.println("\nCredits: " + credits.setScale(2, RoundingMode.HALF_UP) + " CR");
            System.out.println("(1) Add Credits");
            System.out.println("(2) Select Item");
            System.out.println("(3) Finish & Return Change");
            System.out.print("Choose: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                credits = addCredits(scanner, credits);
            } else if (choice.equals("2")) {
                credits = selectItem(scanner, inventory, credits);
            } else if (choice.equals("3")) {
                credits = returnChange(credits);
                transaction = false;
            } else {
                System.out.println("ERROR: Invalid entry.");
            }
        }

        return credits;
    }

    private static BigDecimal addCredits(Scanner scanner, BigDecimal credits) {
        System.out.print("Enter credits (positive whole numbers only): ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            System.out.println("ERROR: Empty input.");
            return credits;
        }

        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                System.out.println("ERROR: Numbers only.");
                return credits;
            }
        }

        int whole = Integer.parseInt(input);
        if (whole <= 0) {
            System.out.println("ERROR: Must be > 0.");
            return credits;
        }

        BigDecimal amount = new BigDecimal(whole);
        credits = credits.add(amount);

        System.out.println("Credits updated: " + credits.setScale(2, RoundingMode.HALF_UP) + " CR");
        VendingLog.logCredits(amount, credits); // VendingLog must also be in com.codervend

        return credits;
    }

    private static BigDecimal selectItem(Scanner scanner, Inventory inventory, BigDecimal credits) {
        System.out.print("Enter item code: ");
        String code = scanner.nextLine().trim().toUpperCase();

        Items item = inventory.getItem(code);

        if (item == null) {
            System.out.println("ERROR: Unknown code.");
            return credits;
        }

        if (item.getQuantity() == 0) {
            System.out.println("SOLD OUT: " + item.getName());
            return credits;
        }

        if (credits.compareTo(item.getPrice()) < 0) {
            System.out.println("ERROR: Insufficient credits. Add more or pick another item.");
            return credits;
        }

        BigDecimal price = item.getPrice();
        credits = credits.subtract(price);
        item.setQuantity(item.getQuantity() - 1);

        System.out.println("Dispensed: " + item.getName() + " (" + price.setScale(2, RoundingMode.HALF_UP) + " CR)");
        VendingLog.logItemsBrought(code, item.getName(), price, credits);

        totalSales = totalSales.add(price);
        salesReport.put(item.getName(), salesReport.getOrDefault(item.getName(), 0) + 1);

        // Fun category messages
        String category = item.getCategory().toLowerCase();
        switch (category) {
            case "debug" -> System.out.println("🐛 Squashed. (Probably.)");
            case "compile" -> System.out.println("🟢 Build succeeded. You earned this.");
            case "coffee" -> System.out.println("☕ Runtime boosted.");
            case "keyboard" -> System.out.println("⌨️ +10 WPM (placebo effect).");
            default -> System.out.println("✨ Enjoy your loot.");
        }

        System.out.println("Remaining credits: " + credits.setScale(2, RoundingMode.HALF_UP) + " CR");
        return credits;
    }

    private static BigDecimal returnChange(BigDecimal credits) {
        int cents = credits.multiply(BigDecimal.valueOf(100)).intValue();

        int quarters = cents / 25;
        int dimes = (cents % 25) / 10;
        int nickels = ((cents % 25) % 10) / 5;

        System.out.println("Change: " + quarters + "q " + dimes + "d " + nickels + "n");
        VendingLog.changeReturned(credits);

        System.out.println("Thanks! Tip: `git status` before you panic.");
        return BigDecimal.ZERO;
    }

    private static void generateDevReport(Inventory inventory, Map<String, Integer> salesReport, BigDecimal totalSales) {
        DevSalesReport.generate(inventory, salesReport, totalSales);
    }
}
