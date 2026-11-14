import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class DateRow extends JPanel {

    public DateRow(ChartRow chartRow) {
        setLayout(new BorderLayout());

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));

       
        center.add(new JButton("<"));
        center.add(new JLabel("11/14/2026"));
        center.add(new JButton(">"));
        JButton exportBtn = new JButton("\u2912");
        exportBtn.addActionListener(e -> {
            try {
                ChartExport.exportToPdf(chartRow.getChart(), "chart.pdf", 800, 400);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        right.add(exportBtn);
        

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        add(center, BorderLayout.CENTER);

        // Don't let this row stretch vertically
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));

        
    }
}
