import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

public class TimeField extends JSpinner {

    public TimeField() {
        super(new SpinnerDateModel());
        setEditor(new JSpinner.DateEditor(this, "hh:mm a"));
        ((JSpinner.DefaultEditor) getEditor()).getTextField().setEditable(false);
    }

    // Returns the chosen time as a LocalTime
    public LocalTime getTime() {
        Date date = (Date) getValue();
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime().withSecond(0).withNano(0);
    }
}