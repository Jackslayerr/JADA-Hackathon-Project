package GeminiAPI;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeminiAPI {
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.1-flash-lite:generateContent";

    public static String analyzeMeal (String mealDescription) throws Exception {
        //Get the API Key from enviroment variable
        String apiKey = System.getenv("GEMINI_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("GEMINI_API_KEY env variable is missing");
        }

        if (mealDescription == null || mealDescription.isBlank()) {
            throw new IllegalStateException("Please provide a meal description");
        }

        //Give Gemini the prompt to return the macros we are looking for
        String prompt = """
            Estimate the nutritional information for this meal:
            %s

            Return ONLY one line of CSV data in this order:
            calories,carbohydrates,protein,fat

            Rules:
            - Use numbers only.
            - Calories must be in kcal.
            - Carbohydrates, protein, and fat must be in grams.
            - Do not include headers, units, or explanations.
            - Estimate reasonable portions if unspecified.
            """.formatted(mealDescription);

        //Escape special characters in the prompt for JSON
        String escapedPrompt = prompt
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t");

        String requestBody = """
            {
                "contents": [{
                    "parts": [{
                        "text": "%s"
                    }]
                }]
            }
            """.formatted(escapedPrompt);

        //Create HTTP request
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(API_URL))
            .header("Content-Type", "application/json")
            .header("x-goog-api-key", apiKey)
            .timeout(Duration.ofSeconds(60))
            .POST(HttpRequest.BodyPublishers.ofString(
                requestBody, StandardCharsets.UTF_8))
            .build(); 

        //Send that HTTP request to Gemini
        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(
            request,
            HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                "Gemini API error: " + response.statusCode()
                + " - " + response.body()
            );
        }

        //Extract the CSV from the Gemini JSON response
        String responseBody = response.body();

        Pattern pattern = Pattern.compile(
            "\"text\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\""
        );

        Matcher matcher = pattern.matcher(responseBody);

        if (!matcher.find()) {
            throw new RuntimeException(
                "Could not find Gemini's response text."
            );
        }

        String csv = matcher.group(1)
            .replace("\\n", "\n")
            .replace("\\r", "")
            .trim();


        //Validate the CSV
        String[] values = csv.split(",");

        if (values.length != 4) {
            throw new RuntimeException(
                "Invalid CSV response from Gemini: " + csv
            );
        }

        for (String value : values) {
            double number = Double.parseDouble(value.trim());

            if (!Double.isFinite(number) || number < 0) {
                throw new RuntimeException(
                    "Invalid nutritional value: " + value
                );
            }
        }

        return csv;
    }
}
