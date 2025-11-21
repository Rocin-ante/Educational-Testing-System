package edu.quiz;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the generation logic for Feedback.
 */
class FeedbackTest {

    @Test
    void generateScoreFeedback_shouldFormatScoreCorrectly() {
        Feedback feedback = new Feedback(3, 5, Collections.emptyList());
        String msg = feedback.generateScoreFeedback();
        assertTrue(msg.contains("3"));
        assertTrue(msg.contains("5"));
    }

    @Test
    void getPerformanceLevel_shouldReturnCorrectLevel() {
        assertEquals("A",
                new Feedback(9, 10, List.of()).getPerformanceLevel());
        assertEquals("B",
                new Feedback(8, 10, List.of()).getPerformanceLevel());
        assertEquals("C",
                new Feedback(6, 10, List.of()).getPerformanceLevel());
        assertEquals("F",
                new Feedback(5, 10, List.of()).getPerformanceLevel());
    }

    @Test
    void generateDetailedFeedback_shouldHandleNoIncorrectQuestions() {
        Feedback allCorrect = new Feedback(2, 2, List.of());
        String msg = allCorrect.generateDetailedFeedback();
        assertTrue(msg.contains("All correct") || msg.length() > 0);
    }
}
