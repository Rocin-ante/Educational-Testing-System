package edu.quiz;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit testing for SimpleQuestion.
 */
class SimpleQuestionTest {

    @Test
    void isCorrect_shouldReturnTrue_whenAnswerMatchesIgnoringCaseAndSpace() {
        SimpleQuestion q = new SimpleQuestion("1+1=?", "2", new String[]{"Basic addition"});

        assertTrue(q.isCorrect("2"));
        assertTrue(q.isCorrect(" 2 "));
        assertFalse(q.isCorrect("3"));
        assertFalse(q.isCorrect(null));
    }

    @Test
    void getFormattedQuestion_shouldContainQuestionTextAndHints() {
        String[] hints = {"Basic addition", "Note that it is an integer"};
        SimpleQuestion q = new SimpleQuestion("1+1=?", "2", hints);

        String formatted = q.getFormattedQuestion();

        assertTrue(formatted.contains("1+1=?"));
        assertTrue(formatted.contains("Basic addition"));
        assertTrue(formatted.contains("Note that it is an integer"));
    }
}
