import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class InputRow extends JPanel {
    private JFormattedTextField startField = makeDateField();
    private JFormattedTextField endField = makeDateField();

    public InputRow() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        

        add(new JLabel("Start Time"));
        add(startField);
        add(new JLabel("-----"));
        add(endField);
        add(new JLabel("End Time"));

        setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));

    }
        private JFormattedTextField makeDateField() {
        try {
            MaskFormatter mask = new MaskFormatter("##/##/####");
            mask.setPlaceholderCharacter('_');
            JFormattedTextField field = new JFormattedTextField(mask);
            field.setColumns(6);
            return field;
        } catch (ParseException e) {
            return new JFormattedTextField(); // only happens if the mask text is invalid
        }
    }
}
