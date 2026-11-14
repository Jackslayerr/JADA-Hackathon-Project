import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class InputRow extends JPanel {
    private TimeField timeField = new TimeField();

    public InputRow() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        

        add(new JLabel("Start Time"));
        //add(startField);
        TimeField startTimeField = new TimeField();
        add(startTimeField);
        add(new JLabel("-----"));
        //add(endField);
        TimeField endTimeField = new TimeField();
        add(endTimeField);
        add(new JLabel("End Time"));

        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));

    }
}
