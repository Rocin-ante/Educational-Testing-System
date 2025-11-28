package edu.quiz;

import com.google.gson.*;
import java.net.http.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads questions from an external API (Open Trivia DB).
 * Converts the JSON response into ApiQuestion objects.
 */
public class ApiQuestionLoader {

    private final QuestionFactory factory;

    public ApiQuestionLoader(QuestionFactory factory) {
        this.factory = factory;
    }

    /**
     * Fetch API questions.
     * @param count Number of questions to fetch.
     * @return List of ApiQuestion objects.
     */
    public List<Question> fetchApiQuestions(int count) {
        List<Question> results = new ArrayList<>();

        try {
            String url = "https://opentdb.com/api.php?amount=" + count + "&type=multiple";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();

            // Parse JSON using Gson
            JsonObject root = JsonParser.parseString(json).getAsJsonObject();
            JsonArray items = root.getAsJsonArray("results");

            for (JsonElement item : items) {
                JsonObject obj = item.getAsJsonObject();

                String questionText = obj.get("question").getAsString();
                String correctAnswer = obj.get("correct_answer").getAsString();
                String difficulty = obj.get("difficulty").getAsString();

                // Convert to ApiQuestion object
                Question q = factory.createApiQuestion(
                        json,               // raw API data
                        questionText,
                        correctAnswer,
                        difficulty
                );

                results.add(q);
            }

        } catch (Exception e) {
            System.out.println("API ERROR: " + e.getMessage());
        }

        return results;
    }
}
