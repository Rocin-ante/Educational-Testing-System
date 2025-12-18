package edu.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Questions from external APIs.
 */
public class ApiQuestion extends Question {

    private String rawApiData; // <--- The Factory is trying to set this
    private String difficulty;
    private List<String> options;

    /**
     * Constructor with 5 arguments (Matches QuestionFactory)
     */
    public ApiQuestion(String rawApiData,
                       String questionText,
                       String correctAnswer,
                       List<String> incorrectAnswers,
                       String difficulty) {

        super(questionText, correctAnswer);
        this.rawApiData = rawApiData; // Store the raw data
        this.difficulty = difficulty;

        // Combine and shuffle answers
        this.options = new ArrayList<>();
        this.options.add(correctAnswer);
        if (incorrectAnswers != null) {
            this.options.addAll(incorrectAnswers);
        }
        Collections.shuffle(this.options);
    }

    @Override
    public boolean isCorrect(String answer) {
        if (answer == null || answer.trim().isEmpty()) return false;
        String cleanAnswer = answer.trim();

        if (correctAnswer.equalsIgnoreCase(cleanAnswer)) return true;

        if (cleanAnswer.length() == 1) {
            char choice = cleanAnswer.toUpperCase().charAt(0);
            int index = choice - 'A';
            if (index >= 0 && index < options.size()) {
                return options.get(index).equalsIgnoreCase(correctAnswer);
            }
        }
        return false;
    }

    @Override
    public String getFormattedQuestion() {
        StringBuilder sb = new StringBuilder();
        sb.append("[API Question][").append(difficulty).append("] ")
                .append(questionText).append("\n\n");

        char label = 'A';
        for (String option : options) {
            sb.append(label).append(") ").append(option).append("\n");
            label++;
        }
        return sb.toString();
    }

    @Override
    public boolean isApiQuestion() {
        return true;
    }

    public String getRawApiData() { return rawApiData; }
    public String getDifficulty() { return difficulty; }
}