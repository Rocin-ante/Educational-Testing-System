package edu.quiz;

import java.util.Map;

/**
 * Multiple choice question type.
 * Use options to store options, such as {"A": "Option 1", "B": "Option 2"},
 * CorrectAnswer stores the key for the correct option (such as "A").
 */
public class MultipleChoiceQuestion extends Question {

    private Map<String, String> options;

    public MultipleChoiceQuestion(String questionText,
                                  Map<String, String> options,
                                  String correctKey) {
        super(questionText, correctKey);
        this.options = options;
    }

    @Override
    public boolean isCorrect(String answer) {
        if (answer == null) return false;
        return correctAnswer.equalsIgnoreCase(answer.trim());
    }

    @Override
    public String getFormattedQuestion() {
        StringBuilder sb = new StringBuilder(questionText).append("\n");
        if (options != null) {
            options.forEach((key, value) ->
                    sb.append(key).append(". ").append(value).append("\n"));
        }
        return sb.toString();
    }
}
