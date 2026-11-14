import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.ParseException;
import java.time.LocalTime;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class InputRow extends JPanel {
    TimeField startTimeField = new TimeField();
    TimeField endTimeField = new TimeField();
    

    public InputRow() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        
        startTimeField.setTime(LocalTime.of(0, 0));
        endTimeField.setTime(LocalTime.of(23, 59));

        add(new JLabel("Start Time"));
        add(startTimeField);
        add(new JLabel("-----"));
        add(endTimeField);
        add(new JLabel("End Time"));

        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));

    }
    public LocalTime getStartTime() {
        return startTimeField.getTime();
    }
    public LocalTime getEndTime() {
        return endTimeField.getTime();
    }
    public TimeField getStartTimeField() {
        return startTimeField;
    }
    public TimeField getEndTimeField() {
        return endTimeField;
    }
}
