
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.jfree.chart.plot.ValueMarker;

public class ChartRow extends JPanel {
    private static final String DATA_FILE = "bsl_5min_moderate.csv";

    private static final Color MEAL_COLOR = new Color(230, 126, 34);

    private XYPlot plot;

    public ChartRow() {
        setLayout(new BorderLayout());

        // Two buttons on the left end
        JPanel left = new JPanel(new GridBagLayout());
        JButton leftButton = new JButton("<-");
        left.add(leftButton);

        // Two buttons on the right end
        JPanel right = new JPanel(new GridBagLayout());
        JButton rightButton = new JButton("->");
        right.add(rightButton);

        add(left, BorderLayout.WEST);
        add(makeChart(), BorderLayout.CENTER);
        add(right, BorderLayout.EAST);

        // Whenever the row is resized, resize the buttons to match
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int width = getWidth() * 8 / 100;    // 8% of row width
                int height = getHeight() * 60 / 100; // 25% of row height

                leftButton.setPreferredSize(new Dimension(width, height));
                rightButton.setPreferredSize(new Dimension(width, height));
                revalidate();
            }
        });
    }


    private ChartPanel makeChart() {
        // x = hour of day (0-24), y = blood sugar
    XYSeries series = new XYSeries("Blood Sugar");
        loadReadings(series);

    JFreeChart chart = ChartFactory.createXYLineChart("Blood Sugar vs Time", "Hour of Day",
    "Blood Sugar (mg/dL)", new XYSeriesCollection(series));

        plot = chart.getXYPlot();

    NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
    xAxis.setRange(0, 24);                       // always show the full day
    xAxis.setTickUnit(new NumberTickUnit(2));    // label every 2 hours

    ((NumberAxis) plot.getRangeAxis()).setAutoRangeIncludesZero(false);   // y-axis fits the data

    chart.removeLegend();   // only one line, so no legend

    ChartPanel panel = new ChartPanel(chart);
    panel.setPreferredSize(new Dimension(500, 300));
    panel.setPopupMenu(null);
    return panel;
    }

    private void loadReadings(XYSeries series) {
        try {
            List<CsvExtrapolator.Record> records = CsvExtrapolator.readRecords(DATA_FILE);
            if (records.isEmpty()) return;

            String day = records.get(0).getDate();
            for (CsvExtrapolator.Record r : records) {
                if (!r.getDate().equals(day)) continue;
                try {
                    LocalTime t = LocalTime.parse(r.getTime());
                    series.add(t.getHour() + t.getMinute() / 60.0, r.getBloodSugar());
                } catch (DateTimeParseException e) {
                    // skip bad rows
                }
            }
        } catch (IOException e) {
            System.err.println("Could not read " + DATA_FILE + ": " + e.getMessage());
        }
    }


    public void addMealMarker(double hour, String label) {
        ValueMarker marker = new ValueMarker(hour);
        marker.setPaint(MEAL_COLOR);
        marker.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                1.0f, new float[] {6f, 4f}, 0f));
        marker.setLabel(label);
        marker.setLabelFont(new Font("SansSerif", Font.PLAIN, 11));
        marker.setLabelPaint(MEAL_COLOR);
        plot.addDomainMarker(marker);
    }

    // Erases all the meal lines
    public void clearMealMarkers() {
        plot.clearDomainMarkers();
    }
    
}
