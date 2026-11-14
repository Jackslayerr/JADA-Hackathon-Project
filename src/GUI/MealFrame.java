
import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class MealFrame extends JFrame {

    public MealFrame() {
        setTitle("Meal Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Stack everything vertically
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        ChartRow chartRow = new ChartRow();

        add(new DateRow());
        add(chartRow);
        add(new InputRow());
        add(new TitleRow());
        add(new MealListRow(chartRow));
        pack();
        setLocationRelativeTo(null); // center on screen
    }
}
