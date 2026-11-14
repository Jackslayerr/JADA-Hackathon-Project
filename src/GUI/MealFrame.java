import GUI.ChartRow;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class MealFrame extends JFrame {

    public MealFrame() {
        setTitle("Meal Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Stack everything vertically
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(new DateRow());      // Row 1: buttons + DATE + buttons
        add(new ChartRow());     // Row 2: buttons + chart + buttons
        add(new InputRow());     // Row 3: two text boxes
        add(new TitleRow());     // Row 4: "Meals"
        add(new MealListRow());  // Row 5: list of text

        pack();
        setLocationRelativeTo(null); // center on screen
    }
}
