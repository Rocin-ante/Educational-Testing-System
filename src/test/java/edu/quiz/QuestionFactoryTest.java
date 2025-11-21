package edu.quiz;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Regarding the testing of the QuestionFactory factory method,
 * Ensure that the returned object type is correct and the key fields are initialized correctly.
 */
class QuestionFactoryTest {

    private final QuestionFactory factory = new QuestionFactory();

    @Test
    void createSimpleQuestion_shouldReturnSimpleQuestion() {
        Question q = factory.createSimpleQuestion("Q1", "Ans", new String[]{"hint"});
        assertTrue(q instanceof SimpleQuestion);
        assertEquals("Q1", q.getQuestionText());
        assertEquals("Ans", q.getCorrectAnswer());
    }

    @Test
    void createMultipleChoiceQuestion_shouldReturnMultipleChoiceQuestion() {
        Map<String, String> options = new HashMap<>();
        options.put("A", "opt1");
        Question q = factory.createMultipleChoiceQuestion("Q2", options, "A");

        assertTrue(q instanceof MultipleChoiceQuestion);
        assertEquals("Q2", q.getQuestionText());
        assertEquals("A", q.getCorrectAnswer());
    }

    @Test
    void createApiQuestion_shouldReturnApiQuestion() {
        Question q = factory.createApiQuestion(
                "{json}", "Q3", "Correct", "easy");

        assertTrue(q instanceof ApiQuestion);
        assertEquals("Q3", q.getQuestionText());
        assertEquals("Correct", q.getCorrectAnswer());
    }
}
