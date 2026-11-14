
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
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

public class ChartRow extends JPanel {

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

    // Sample line chart for reference
    private ChartPanel makeChart() {
        // x = hour of day (0-24), y = blood sugar
    XYSeries series = new XYSeries("Blood Sugar");
    series.add(7, 95);
    series.add(8, 130);
    series.add(9, 118);
    series.add(12, 140);
    series.add(15, 110);
    series.add(18, 125);
    series.add(21, 105);

    JFreeChart chart = ChartFactory.createXYLineChart("Blood Sugar vs Time", "Hour of Day",
    "Blood Sugar (mg/dL)", new XYSeriesCollection(series));

    XYPlot plot = chart.getXYPlot();

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
    
}
