package edu.quiz;

import com.google.gson.*;
import java.net.http.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads questions from the Open Trivia Database API.
 * Handles JSON parsing and converts API results into ApiQuestion objects.
 */
public class ApiQuestionLoader {

    private final QuestionFactory factory;

    public ApiQuestionLoader(QuestionFactory factory) {
        this.factory = factory;
    }

    /**
     * Fetches multiple-choice questions from Open Trivia DB.
     * @param count Number of questions to retrieve
     * @return List of Question objects created from API data
     */
    public List<Question> fetchApiQuestions(int count) {
        List<Question> results = new ArrayList<>();

        try {
            // URL for multiple choice questions
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

                String questionText = htmlDecode(obj.get("question").getAsString());
                String correctAnswer = htmlDecode(obj.get("correct_answer").getAsString());
                String difficulty = obj.get("difficulty").getAsString();

                // --- NEW CODE: Extract Incorrect Answers ---
                List<String> incorrectAnswers = new ArrayList<>();
                JsonArray incArr = obj.getAsJsonArray("incorrect_answers");
                for (JsonElement el : incArr) {
                    incorrectAnswers.add(htmlDecode(el.getAsString()));
                }
                // -------------------------------------------

                // Create ApiQuestion via Factory (Now passing incorrectAnswers)
                Question q = factory.createApiQuestion(
                        json,
                        questionText,
                        correctAnswer,
                        incorrectAnswers, // <--- Added this parameter
                        difficulty
                );

                results.add(q);
            }

        } catch (Exception e) {
            System.out.println("API ERROR: " + e.getMessage());
            e.printStackTrace();
        }

        return results;
    }

    /** Simple HTML entity decoder */
    private String htmlDecode(String text) {
        if (text == null) return "";
        return text.replace("&quot;", "\"")
                .replace("&#039;", "'")
                .replace("&amp;", "&")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&eacute;", "é")
                .replace("&rsquo;", "'");
    }
}