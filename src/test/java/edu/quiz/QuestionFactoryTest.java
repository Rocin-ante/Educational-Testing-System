package edu.quiz;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class QuestionFactoryTest {

    private final QuestionFactory factory = new QuestionFactory();

    @Test
    void testCreateSimpleQuestion() {
        Question q = factory.createSimpleQuestion("What is 2+2?", "4", new String[]{"Math", "Addition"});
        assertNotNull(q);
        assertTrue(q instanceof SimpleQuestion);
        assertEquals("What is 2+2?", q.getQuestionText());
    }

    @Test
    void testCreateMultipleChoiceQuestion() {
        Map<String, String> options = new HashMap<>();
        options.put("A", "Option A");
        options.put("B", "Option B");

        Question q = factory.createMultipleChoiceQuestion("Choose A", options, "A");
        assertNotNull(q);
        assertTrue(q instanceof MultipleChoiceQuestion);
    }

    @Test
    void testCreateApiQuestion() {
        // Prepare dummy incorrect answers for the new parameter
        List<String> wrongAnswers = Arrays.asList("Wrong 1", "Wrong 2");

        // Now passing 5 arguments: (RawData, Text, Correct, IncorrectList, Difficulty)
        Question q = factory.createApiQuestion(
                "{json}",
                "API Question Text",
                "Correct Answer",
                wrongAnswers,
                "easy"
        );

        assertNotNull(q);
        assertTrue(q instanceof ApiQuestion);
        // Cast to ApiQuestion to check specific fields
        ApiQuestion apiQ = (ApiQuestion) q;
        assertEquals("easy", apiQ.getDifficulty());
        assertTrue(apiQ.isApiQuestion());
    }
}