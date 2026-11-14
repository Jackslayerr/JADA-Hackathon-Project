import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MealListRow extends JPanel {

    public MealListRow() {
        setLayout(new BorderLayout());

        String[] meals = {
            "Breakfast - Eggs and toast",
            "Lunch - Chicken salad",
            "Snack - Apple",
            "Dinner - Pasta"
        };

        JScrollPane scroll = new JScrollPane(new JList<String>(meals));
        scroll.setPreferredSize(new Dimension(600, 120));
        add(scroll, BorderLayout.CENTER);
    }
}
