package edu.quiz;

import java.util.Arrays;

/**
 * Short answer question type.
 * Characteristics: Only the question stem, standard answers, and several prompts are available.
 */
public class SimpleQuestion extends Question {

    /** Prompt information array, can be empty */
    private String[] hints;

    public SimpleQuestion(String questionText, String correctAnswer, String[] hints) {
        super(questionText, correctAnswer);
        this.hints = hints == null ? new String[0] : hints;
    }

    @Override
    public boolean isCorrect(String answer) {
        if (answer == null) return false;
        return correctAnswer.equalsIgnoreCase(answer.trim());
    }

    @Override
    public String getFormattedQuestion() {
        if (hints.length == 0) {
            return questionText;
        }
        return questionText + "\nPrompt: " + Arrays.toString(hints);
    }
}
