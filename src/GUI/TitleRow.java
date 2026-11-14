import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitleRow extends JPanel {

    public TitleRow() {
        setLayout(new FlowLayout(FlowLayout.CENTER));

        add(new JLabel("Meals"));

        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
    }
}
