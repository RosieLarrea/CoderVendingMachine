package com.codervend;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class VendingLog {

    private static final String LOG_FILE = "DevVendLog.txt";
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

    private static String timestamp() {
        return LocalDateTime.now().format(DTF) + " Z ";
    }

    public static void writeLog(String entry) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(entry + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("ERROR: Unable to write to log file: " + LOG_FILE);
            e.printStackTrace();
        }
    }

    public static void logCredits(BigDecimal amount, BigDecimal currentCredits) {
        String entry = timestamp()
                + "FEED CREDITS: "
                + amount.setScale(2, RoundingMode.HALF_UP) + " CR "
                + currentCredits.setScale(2, RoundingMode.HALF_UP) + " CR";

        writeLog(entry);
    }

    public static void logItemsBrought(String code, String name, BigDecimal price, BigDecimal remainingCredits) {
        String entry = timestamp()
                + "DISPENSE: "
                + code + " "
                + name + " "
                + price.setScale(2, RoundingMode.HALF_UP) + " CR "
                + remainingCredits.setScale(2, RoundingMode.HALF_UP) + " CR";

        writeLog(entry);
    }

    public static void changeReturned(BigDecimal remainingCredits) {
        BigDecimal finalBalance = BigDecimal.ZERO;

        String entry = timestamp()
                + "RETURN CHANGE: "
                + remainingCredits.setScale(2, RoundingMode.HALF_UP) + " CR "
                + finalBalance.setScale(2, RoundingMode.HALF_UP) + " CR";

        writeLog(entry);
    }

    public static void logReportTotal(BigDecimal totalCredits) {
        String entry = timestamp()
                + "REPORT TOTAL: "
                + totalCredits.setScale(2, RoundingMode.HALF_UP) + " CR";

        writeLog(entry);
    }
}
