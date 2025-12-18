package edu.quiz;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class ApiQuestionLoaderTest {

    @Test
    void testFetchApiQuestions() {
        QuestionFactory factory = new QuestionFactory();
        ApiQuestionLoader loader = new ApiQuestionLoader(factory);

        System.out.println("Testing API connection...");
        List<Question> apiQuestions = loader.fetchApiQuestions(3);

        // Assertions (Checks)
        assertNotNull(apiQuestions, "The returned list should not be null.");
        assertEquals(3, apiQuestions.size(), "Should have received exactly 3 questions.");

        for (Question q : apiQuestions) {
            assertNotNull(q.getQuestionText(), "Question text should not be null");
            assertFalse(q.getQuestionText().isEmpty(), "Question text should not be empty");


            System.out.println("Verified API Question: " + q.getFormattedQuestion());
        }
    }
}