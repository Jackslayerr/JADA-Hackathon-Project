import javax.swing.SwingUtilities;
import GeminiAPI.GeminiAPI;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MealFrame().setVisible(true));


        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your meal: ");
        String meal = scanner.nextLine();

        try {
            String csv = GeminiAPI.analyzeMeal(meal);

            String[] macros = csv.split(",");

            double calories = Double.parseDouble(macros[0]);
            double carbs = Double.parseDouble(macros[1]);
            double protein = Double.parseDouble(macros[2]);
            double fat = Double.parseDouble(macros[3]);

            System.out.println("Calories: " + calories);
            System.out.println("Carbs: " + carbs + "g");
            System.out.println("Protein: " + protein + "g");
            System.out.println("Fat: " + fat + "g");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }



        }
    }
