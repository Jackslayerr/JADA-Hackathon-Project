import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DateRow extends JPanel {

    public DateRow() {
        setLayout(new BorderLayout());

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));

        left.add(new JButton("+"));
        center.add(new JButton("<"));
        center.add(new JLabel("DATE"));
        center.add(new JButton(">"));
        right.add(new JButton(">>"));

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        add(center, BorderLayout.CENTER);

        // Don't let this row stretch vertically
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
    }
}
