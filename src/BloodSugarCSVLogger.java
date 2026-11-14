package src;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BloodSugarCSVLogger {

    private static final String FILE_NAME = "bloodsugar_log.csv";

    public static void main(String[] args) {
        // Initialize the CSV file with headers if it does not already exist
        initializeCSVFile();

        // Record a new blood sugar reading (e.g., 115 mg/dL)
        logBloodSugarReading(115);

        // Record another reading with a different value (e.g., 98 mg/dL)
        logBloodSugarReading(98);

        System.out.println("Blood sugar records successfully updated in " + FILE_NAME);
    }

    // Checks if CSV file exists, if not creates it and writes the header row.
    private static void initializeCSVFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("Date,Time,BloodSugar_mg_dL");
                writer.newLine();
                System.out.println("Created new CSV file and added headers.");
            } catch (IOException e) {
                System.err.println("Error initializing CSV file: " + e.getMessage());
            }
        }
    }

    // Measured BSL in mg/dL
    private static void logBloodSugarReading(int bloodSugarLevel, String date, String time) {

        // Open the FileWriter in append mode (true parameter)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(date + "," + time + "," + bloodSugarLevel);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing blood sugar record: " + e.getMessage());
        }
    }
}