package edu.quiz;

import java.util.Map;

/**
 * Factory Pattern, which centrally manages the creation logic of problems.
 * Quiz only needs to call the factory method to obtain the Question object,
 * No need to understand the construction details of specific subclasses, reducing coupling.
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

    public Question createApiQuestion(String rawData,
                                      String text,
                                      String correct,
                                      String difficulty) {
        return new ApiQuestion(rawData, text, correct, difficulty);
    }
}
