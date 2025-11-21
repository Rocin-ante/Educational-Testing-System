package edu.quiz;

import java.util.*;

/**
 * Simple console testing is used to verify whether the core logic of Task 2 is working properly.
 */
public class Main {
    public static void main(String[] args) {

        QuestionFactory factory = new QuestionFactory();
        Quiz quiz = new Quiz();

        // 1. Simple Question
        quiz.addQuestion(factory.createSimpleQuestion(
                "1 + 1 = ?",
                "2",
                new String[]{"Basic addition", "Note that it is an integer"}
        ));

        // 2. MCQ
        Map<String, String> opts = new LinkedHashMap<>();
        opts.put("A", "Earth");
        opts.put("B", "Mars");
        opts.put("C", "Jupiter");

        quiz.addQuestion(factory.createMultipleChoiceQuestion(
                "Which of the following is Mars？",
                opts,
                "B"
        ));

        // 3. API Question (using fake data first)
        quiz.addQuestion(factory.createApiQuestion(
                "{ \"raw\": \"example\" }",
                "Example API question: Is the answer A or B?",
                "A",
                "medium"
        ));

        // Simulate student answers
        List<String> answers = Arrays.asList("2", "B", "C");

        int score = quiz.calculateScore(answers);
        List<Question> wrong = quiz.getIncorrectQuestions(answers);

        Feedback feedback = new Feedback(score, quiz.getTotalQuestions(), wrong);

        System.out.println(feedback.generateScoreFeedback());
        System.out.println("Performance level：" + feedback.getPerformanceLevel());
        System.out.println(feedback.generateDetailedFeedback());
    }
}
