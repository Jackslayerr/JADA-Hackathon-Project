import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvExtrapolator
{
    public static class Record {
        private final String date;
        private final String time;
        private final double bloodSugar;

        public Record(String date, String time, double bloodSugar) {
            this.date = date;
            this.time = time;
            this.bloodSugar = bloodSugar;
        }

        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public double getBloodSugar() {
            return bloodSugar;
        }
    }

    public static List<Record> readRecords(String filePath) throws IOException {
        List<Record> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 3) continue;

                try {
                    // Read variables directly as strings and double from CSV
                    String date = parts[0].trim();
                    String time = parts[1].trim();
                    double bloodSugar = Double.parseDouble(parts[2].trim());

                    records.add(new Record(date, time, bloodSugar));
                } catch (NumberFormatException e) {
                    // Automatically skips header rows or malformed numeric rows
                }
            }
        }

        return records;
    }
}
