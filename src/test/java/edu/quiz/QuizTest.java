package edu.quiz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A test for Quiz scoring and logic of collecting incorrect questions.
 */
class QuizTest {

    private Quiz quiz;

    @BeforeEach
    void setUp() {
        quiz = new Quiz();
        QuestionFactory factory = new QuestionFactory();

        quiz.addQuestion(factory.createSimpleQuestion(
                "1+1=?", "2", new String[]{"Basic addition"}));

        Map<String, String> options = new LinkedHashMap<>();
        options.put("A", "Earth");
        options.put("B", "Mars");
        options.put("C", "Jupiter");
        quiz.addQuestion(factory.createMultipleChoiceQuestion(
                "Which of the following is Mars？", options, "B"));
    }

    @Test
    void calculateScore_shouldCountCorrectAnswers() {
        List<String> answers = Arrays.asList("2", "B");
        int score = quiz.calculateScore(answers);
        assertEquals(2, score);

        List<String> wrongAnswers = Arrays.asList("3", "A");
        int score2 = quiz.calculateScore(wrongAnswers);
        assertEquals(0, score2);
    }

    @Test
    void getIncorrectQuestions_shouldReturnOnlyWrongOnes() {
        List<String> answers = Arrays.asList("2", "A"); // 第二题错
        List<Question> incorrect = quiz.getIncorrectQuestions(answers);

        assertEquals(1, incorrect.size());
        assertEquals("Which of the following is Mars？", incorrect.get(0).getQuestionText());
    }

    @Test
    void getCurrentQuestionAndMoveToNext_shouldNavigateCorrectly() {
        Question q1 = quiz.getCurrentQuestion();
        assertEquals("1+1=?", q1.getQuestionText());

        boolean moved = quiz.moveToNextQuestion();
        assertTrue(moved);

        Question q2 = quiz.getCurrentQuestion();
        assertEquals("Which of the following is Mars？", q2.getQuestionText());

        // 已经是最后一题，再 move 应该返回 false
        assertFalse(quiz.moveToNextQuestion());
    }
}
