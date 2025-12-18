package edu.quiz;

import java.util.List;
import java.util.Map;

/**
 * Factory Pattern, which centrally manages the creation logic of problems.
 */
public class QuestionFactory {

    public Question createSimpleQuestion(String text,
                                         String answer,
                                         String[] hints) {
        return new SimpleQuestion(text, answer, hints);
    }

    public Question createMultipleChoiceQuestion(String text,
                                                 Map<String, String> options,
                                                 String correctKey) {
        return new MultipleChoiceQuestion(text, options, correctKey);
    }

    /**
     * UPDATED: Accepts List<String> for incorrect answers.
     * This fixes the "String cannot be converted to List" error.
     */
    public Question createApiQuestion(String rawData,
                                      String text,
                                      String correct,
                                      List<String> incorrectAnswers, // <--- New parameter
                                      String difficulty) {
        // Now it matches the new ApiQuestion constructor
        return new ApiQuestion(rawData, text, correct, incorrectAnswers, difficulty);
    }
}