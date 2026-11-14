import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvExtrapolator
{
    public static List<DataPoint> readAndExtrapolate(String filePath, int stepsToExtrapolate) throws IOException
    {
        List<DataPoint> points = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("'");

                if (parts.length < 2) continue;

                try
                {
                    double x = Double.parseDouble(parts[0].trim());
                    double y = Double.parseDouble(parts[1].trim());
                    points.add(new DataPoint(x,y,false));
                }
                catch (NumberFormatException e)
                {
                    // Skips header rows and/or improper lines
                }
            }
        }

        if (points.size() < 2)
        {
            throw new IllegalArgumentException("CSV must contain at least 2 valid numeric rows to extrapolate.");
        }

        int n = points.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for (DataPoint p : points)
        {
            sumX += p.x;
            sumY += p.y;
            sumXY += p.x * p.y;
            sumX2 += p.x * p.x;
        }
    }


}
