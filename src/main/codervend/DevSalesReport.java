package com.codervend;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class DevSalesReport {

    public static void generate(Inventory inventory,
                                Map<String, Integer> salesReport,
                                BigDecimal totalSales) {

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String filename = "DevSalesReport_" + timestamp + ".txt";

        try (FileWriter writer = new FileWriter(filename, false)) {

            writer.write("=== DEV-O-MATIC SALES REPORT ===" + System.lineSeparator());
            writer.write("ITEM | UNITS SOLD" + System.lineSeparator());
            writer.write("-------------------------------" + System.lineSeparator());

            for (Items item : inventory.getAllItems().values()) {
                int soldCount = salesReport.getOrDefault(item.getName(), 0);
                writer.write(item.getName() + " | " + soldCount + System.lineSeparator());
            }

            writer.write(System.lineSeparator());
            writer.write("TOTAL CREDITS EARNED: "
                    + totalSales.setScale(2, RoundingMode.HALF_UP)
                    + " CR" + System.lineSeparator());

            System.out.println("Sales report generated at:");
            System.out.println(new File(filename).getAbsolutePath());

            VendingLog.writeLog(
                    "DEV REPORT TOTAL: "
                            + totalSales.setScale(2, RoundingMode.HALF_UP)
                            + " CR"
            );

        } catch (IOException e) {
            System.out.println("ERROR: Unable to generate sales report.");
            e.printStackTrace();
        }
    }
}
