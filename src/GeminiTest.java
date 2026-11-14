
import GeminiAPI.GeminiAPI;
import java.util.Scanner;

public class GeminiTest {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your meal: ");
        String meal = scanner.nextLine();

        try {
            System.out.println("Asking Gemini...");

            String csv = GeminiAPI.analyzeMeal(meal);

            String[] macros = csv.split(",");

            double calories = Double.parseDouble(macros[0]);
            double carbs = Double.parseDouble(macros[1]);
            double protein = Double.parseDouble(macros[2]);
            double fat = Double.parseDouble(macros[3]);

            System.out.println("\nEstimated Nutrition:");
            System.out.println("Calories: " + calories);
            System.out.println("Carbs: " + carbs + "g");
            System.out.println("Protein: " + protein + "g");
            System.out.println("Fat: " + fat + "g");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}
