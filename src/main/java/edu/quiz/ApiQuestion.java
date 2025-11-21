package edu.quiz;

/**
 * Questions from external APIs.
 * Currently primarily used to differentiate sources and preserve original data and difficulty information.
 * Actual JSON parsing can be supplemented in Task 5.
 */
public class ApiQuestion extends Question {

    /** API returned raw data (e.g., a JSON string). */
    private String rawApiData;

    /** Difficulty levels, such as easy / medium / hard */
    private String difficulty;

    public ApiQuestion(String rawApiData,
                       String questionText,
                       String correctAnswer,
                       String difficulty) {
        super(questionText, correctAnswer);
        this.rawApiData = rawApiData;
        this.difficulty = difficulty;
    }

    @Override
    public boolean isCorrect(String answer) {
        if (answer == null) return false;
        return correctAnswer.equalsIgnoreCase(answer.trim());
    }

    @Override
    public String getFormattedQuestion() {
        return "[API Question][" + difficulty + "] " + questionText;
    }

    public String getRawApiData() {
        return rawApiData;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
