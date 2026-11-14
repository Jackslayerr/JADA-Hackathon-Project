import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ChartRow extends JPanel {
    private InputRow inputRow;
    private NumberAxis xAxis;
    private boolean swapping = false;   // stops the swap from triggering itself

    public ChartRow(InputRow inputRow) throws IOException {
        this.inputRow = inputRow;
        setLayout(new BorderLayout());

        // Button on the left end
        JPanel left = new JPanel(new GridBagLayout());
        JButton leftButton = new JButton("<-");
        left.add(leftButton);

        // Button on the right end
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
                int height = getHeight() * 60 / 100; // 60% of row height

                leftButton.setPreferredSize(new Dimension(width, height));
                rightButton.setPreferredSize(new Dimension(width, height));
                revalidate();
            }
        });

        // Update the chart whenever either time changes
        inputRow.getStartTimeField().addChangeListener(e -> updateRange());
        inputRow.getEndTimeField().addChangeListener(e -> updateRange());
    }

    private ChartPanel makeChart() throws IOException {
        // x = hour of day (0-24), y = blood sugar
        XYSeries series = new XYSeries("Blood Sugar");
        List<CsvExtrapolator.Record> records = CsvExtrapolator.readRecords("C:\\JADA-Hackathon-Project\\csv\\bsl_5min_mild.csv");
        for (CsvExtrapolator.Record record : records) {
            LocalTime time = LocalTime.parse(record.getTime());
            double numTime = time.getHour() + time.getMinute() / 60.0;
            series.add(numTime, record.getBloodSugar());
        }
        
        JFreeChart chart = ChartFactory.createXYLineChart("Blood Sugar vs Time", "Time",
                "Blood Sugar (mg/dL)", new XYSeriesCollection(series));

        XYPlot plot = chart.getXYPlot();
        xAxis = (NumberAxis) plot.getDomainAxis();

        xAxis.setRange(0, 24);          // full day to start with
        xAxis.setTickUnit(ticks(6));    // a label every 6 hours to start with
        updateRange();                  // then apply the time boxes

        ((NumberAxis) plot.getRangeAxis()).setAutoRangeIncludesZero(false);   // y-axis fits the data

        chart.removeLegend();   // only one line, so no legend

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(500, 300));
        panel.setPopupMenu(null);
        return panel;
    }

    private void updateRange() {
        if (swapping) return;   // ignore the changes caused by the swap itself

        LocalTime start = inputRow.getStartTime();
        LocalTime end = inputRow.getEndTime();

        // If the end is before the start, flip them
        if (end.isBefore(start)) {
            swapping = true;
            inputRow.getStartTimeField().setTime(end);
            inputRow.getEndTimeField().setTime(start);
            swapping = false;

            LocalTime temp = start;   // flip the local copies too
            start = end;
            end = temp;
        }

        // Convert times to decimal hours (7:30 -> 7.5)
        double s = start.getHour() + start.getMinute() / 60.0;
        double e = end.getHour() + end.getMinute() / 60.0;

        if (e > s) {
            xAxis.setRange(s, e);

            // About 4 labels across the window, rounded to the nearest 5 minutes
            double step = Math.round((e - s) / 4 * 12) / 12.0;
            xAxis.setTickUnit(ticks(Math.max(step, 1 / 12.0)));   // never smaller than 5 minutes
        }
    }

    // Tick marks every `step` hours, labeled as real times like "7:30 AM"
    private NumberTickUnit ticks(double step) {
        return new NumberTickUnit(step) {
            public String valueToString(double hours) {
                int minutes = (int) Math.round(hours * 60);
                return LocalTime.of(minutes / 60 % 24, minutes % 60)
                        .format(DateTimeFormatter.ofPattern("h:mm a", Locale.US));
            }
        };
    }
}
