import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class DateRow extends JPanel {

    public DateRow() {
        setLayout(new BorderLayout());

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPopupMenu addData = new JPopupMenu();
        addData.add("Add Exercise");
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton addDataButton = new JButton("+");
        addDataButton.addActionListener(e -> {
            addData.show(addDataButton, 0, 0);
        });
        left.add(addDataButton);
        center.add(new JButton("<"));
        center.add(new JLabel("11/14/2026"));
        center.add(new JButton(">"));
        right.add(new JButton("\u2912"));

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);
        add(center, BorderLayout.CENTER);

        // Don't let this row stretch vertically
        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));

        
    }
}
