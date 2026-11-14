package GUI;

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
import org.jfree.data.category.DefaultCategoryDataset;

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
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        data.addValue(1800, "A1C", "Mon");
        data.addValue(2100, "Calories", "Tue");
        data.addValue(1950, "Calories", "Wed");
        data.addValue(2300, "Calories", "Thu");
        data.addValue(2000, "Calories", "Fri");

        JFreeChart chart = ChartFactory.createLineChart("A1C vs Time", "Time", "A1C", data);
        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(500, 300));
        panel.setPopupMenu(null);
        return panel;
    }
    
}
