import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputRow extends JPanel {

    public InputRow() {
        setLayout(new FlowLayout(FlowLayout.CENTER));

        add(new JTextField(10));
        add(new JTextField(10));

        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
    }
}
