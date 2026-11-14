
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class MealFrame extends JFrame {

    public MealFrame() throws IOException {
        setTitle("Meal Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Stack everything vertically
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        InputRow inputRow = new InputRow();
        ChartRow chartRow = new ChartRow(inputRow);

        add(new DateRow());      // Row 1: buttons + DATE + buttons
        add(chartRow);     // Row 2: buttons + chart + buttons
        add(inputRow);     // Row 3: two text boxes
        add(new TitleRow());     // Row 4: "Meals"
        add(new MealListRow());  // Row 5: list of text

        pack();
        setLocationRelativeTo(null); // center on screen
    }
}
