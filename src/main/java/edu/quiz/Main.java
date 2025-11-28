package edu.quiz;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This main method is ONLY used for simple console testing
 * to verify that the core logic (Question, Quiz, Feedback) works correctly.
 * It is NOT part of the GUI flow. The real application entry point
 * for end users is QuizGuiApp.
 */
public class Main {

    public static void main(String[] args) {

        QuestionFactory factory = new QuestionFactory();
        Quiz quiz = new Quiz();

        // 1. Simple question (console test)
        quiz.addQuestion(factory.createSimpleQuestion(
                "What is 1 + 1?",
                "2",
                new String[]{"Basic arithmetic", "Integer result"}
        ));

        // 2. Multiple-choice question (console test)
        Map<String, String> options = new LinkedHashMap<>();
        options.put("A", "Earth");
        options.put("B", "Mars");
        options.put("C", "Jupiter");
        options.put("D", "Saturn");

        quiz.addQuestion(factory.createMultipleChoiceQuestion(
                "Which planet is known as the Red Planet? (Enter A/B/C/D)",
                options,
                "B"
        ));

        // 3. API-style question (console test placeholder)
        quiz.addQuestion(factory.createApiQuestion(
                "{ \"source\": \"demo\" }",
                "Sample API question: correct answer is C.",
                "C",
                "medium"
        ));

        // Simulated student answers for console test
        List<String> answers = Arrays.asList("2", "B", "C");

        int score = quiz.calculateScore(answers);
        List<Question> wrong = quiz.getIncorrectQuestions(answers);

        Feedback feedback = new Feedback(score, quiz.getTotalQuestions(), wrong);

        System.out.println(feedback.generateScoreFeedback());
        System.out.println("Performance level: " + feedback.getPerformanceLevel());
        System.out.println(feedback.generateDetailedFeedback());
    }
}
