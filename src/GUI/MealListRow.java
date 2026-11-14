import java.awt.BorderLayout;
import java.awt.Dimension;

import GeminiAPI.GeminiAPI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Locale;
import javax.swing.*;

public class MealListRow extends JPanel {

    // User input
    private final JComboBox<String> mealType =
        new JComboBox<>(new String[] {
            "Breakfast", "Lunch", "Snack", "Dinner"
        });

    private final JTextField mealInput = new JTextField(22);
    private final JButton addButton = new JButton ("Add and Analyze");

    private final JComboBox<String> hourDropdown =
        new JComboBox<>();

    private final JComboBox<String> minuteDropdown =
        new JComboBox<>();

    private final JComboBox<String> amPmDropdown =
        new JComboBox<>(new String[] {"AM", "PM"});

    //Changing list of meals
    private final DefaultListModel<MealEntry> mealModel =
        new DefaultListModel();

    private final JList<MealEntry> mealList =
        new JList<>(mealModel);

    
    // Display messages and the total macros
    private final JLabel statusLabel = 
        new JLabel("Enter a meal to analyze");

    private final JLabel totalMacrosLabel = 
        new JLabel("Totals: 0 kcal | 0 g carbs | 0 g protein | 0 g fat");
    
    public MealListRow() {
        setLayout(new BorderLayout(6, 6));

        //construct the dropdown, text field, and add button
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Hours 1 through 12
        for (int hour = 1; hour <= 12; hour++) {
            hourDropdown.addItem(String.valueOf(hour));
        }

        // Minutes 00 through 59
        for (int minute = 0; minute < 60; minute = minute + 10) {
            minuteDropdown.addItem(
                String.format("%02d", minute)
            );
        }

        // Default time
        hourDropdown.setSelectedItem("12");
        minuteDropdown.setSelectedItem("00");
        amPmDropdown.setSelectedItem("PM");
        
        inputPanel.add(mealType);
        inputPanel.add(mealInput);

        inputPanel.add(new JLabel("Time:"));
        inputPanel.add(hourDropdown);
        inputPanel.add(new JLabel(":"));
        inputPanel.add(minuteDropdown);
        inputPanel.add(amPmDropdown);

        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);

        //Meal list
        mealList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(mealList);
        scroll.setPreferredSize(new Dimension(700, 150));

        add(scroll, BorderLayout.CENTER);

        //Bottom section of the interface
        JButton removeButton = new JButton("Remove  Selected");

        JPanel bottom = new JPanel(new BorderLayout());
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));

        actions.add(removeButton);
        actions.add(statusLabel);

        bottom.add(actions, BorderLayout.NORTH);
        bottom.add(totalMacrosLabel, BorderLayout.SOUTH);

        add(bottom, BorderLayout.SOUTH);

        //Button functionaltiy
        addButton.addActionListener(e -> addMeal());
        mealInput.addActionListener(e -> addMeal());

        removeButton.addActionListener(e -> {
            int index = mealList.getSelectedIndex();

            if (index >= 0) {
                mealModel.remove(index);
                updateTotalMacros();
            }
        });

    }
    
    //Send the meals to gemini for estimates

    private void addMeal() {
        String description = mealInput.getText().trim();

        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Enter a meal description first"
            );
            return;
        }

        String type = (String) mealType.getSelectedItem();

        String hour = (String) hourDropdown.getSelectedItem();
        String minute = (String) minuteDropdown.getSelectedItem();
        String amPm = (String) amPmDropdown.getSelectedItem();

        String mealTime = hour + ":" + minute + " " + amPm;
        
        addButton.setEnabled(false);
        mealInput.setEnabled(false);
        mealType.setEnabled(false);
        hourDropdown.setEnabled(false);
        minuteDropdown.setEnabled(false);
        amPmDropdown.setEnabled(false);
        statusLabel.setText("Analyzing your meal...");

        //Runs API request without needed to freeze Swing
        new SwingWorker<MealEntry, Void>() {

            @Override
            protected MealEntry doInBackground() throws Exception {

                String csv = GeminiAPI.analyzeMeal(description);
                String[] values = csv.split(",");

                if (values.length != 4) {
                    throw new IllegalArgumentException("Invalid nutrition response: " + csv); 
                }

                return new MealEntry(
                    type,
                    description,
                    mealTime,
                    Double.parseDouble(values[0].trim()),
                    Double.parseDouble(values[1].trim()),
                    Double.parseDouble(values[2].trim()),
                    Double.parseDouble(values[3].trim())

                );
            }

            @Override
            protected void done () {
                try {
                    mealModel.addElement(get());
                    mealInput.setText("");

                    updateTotalMacros();
                    statusLabel.setText("Meal added (estimated values).");
                } catch (Exception ex) {
                    statusLabel.setText("Couldn't analyze meal");

                    Throwable cause = ex.getCause() == null ? ex : ex.getCause();

                    JOptionPane.showMessageDialog(
                        MealListRow.this,
                        cause.getMessage(),
                        "Gemini error",
                        JOptionPane.ERROR_MESSAGE
                    );
                } finally {
                    addButton.setEnabled(true);
                    mealInput.setEnabled(true);
                    mealType.setEnabled(true);
                    hourDropdown.setEnabled(true);
                    minuteDropdown.setEnabled(true);
                    amPmDropdown.setEnabled(true);
                }
            }
        }.execute();

    }

    //Calculate totals from all the meals in the list
    private void updateTotalMacros() {
        double calories = 0;
        double carbs = 0;
        double protein = 0;
        double fat = 0;

        for (int i = 0; i < mealModel.size(); i++) {
            MealEntry entry = mealModel.get(i);

            calories += entry.calories;
            carbs += entry.carbs;
            protein += entry.protein;
            fat += entry.fat;
        }

        totalMacrosLabel.setText(String.format(
            Locale.US,
            "Totals (estimated): %.0f kcal | %.1f g carbs | %.1f g protein | %.1f g fat",
            calories, carbs, protein, fat
        ));

    }

    // Represents an individual meal and its macros
    private static class MealEntry {

        final String type;
        final String description;
        final String time;
        final double calories;
        final double carbs;
        final double protein;
        final double fat;

        MealEntry(String type, String description, String time,
                  double calories, double carbs,
                  double protein, double fat) {

            this.type = type;
            this.description = description;
            this.time = time;
            this.calories = calories;
            this.carbs = carbs;
            this.protein = protein;
            this.fat = fat;
        }

        @Override
        public String toString() {
            return String.format(
                Locale.US,
                "%s - %s — %s | %.0f kcal, %.1f g carbs, %.1f g protein, %.1f g fat",
                time, type, description, calories, carbs, protein, fat
            );
        }
    }
}
