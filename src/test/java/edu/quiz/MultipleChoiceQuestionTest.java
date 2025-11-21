package edu.quiz;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit testing for Multiple Choice Question.
 */
class MultipleChoiceQuestionTest {

    private MultipleChoiceQuestion createSampleQuestion() {
        Map<String, String> options = new LinkedHashMap<>();
        options.put("A", "Earth");
        options.put("B", "Mars");
        options.put("C", "Jupiter");
        return new MultipleChoiceQuestion("Which of the following is Mars？", options, "B");
    }

    @Test
    void isCorrect_shouldCheckByKeyIgnoringCase() {
        MultipleChoiceQuestion q = createSampleQuestion();

        assertTrue(q.isCorrect("B"));
        assertTrue(q.isCorrect(" b "));
        assertFalse(q.isCorrect("A"));
        assertFalse(q.isCorrect(null));
    }

    @Test
    void getFormattedQuestion_shouldContainQuestionAndAllOptions() {
        MultipleChoiceQuestion q = createSampleQuestion();
        String formatted = q.getFormattedQuestion();

        assertTrue(formatted.contains("Which of the following is Mars？"));
        assertTrue(formatted.contains("A. Earth"));
        assertTrue(formatted.contains("B. Mars"));
        assertTrue(formatted.contains("C. Jupiter"));
    }
}
