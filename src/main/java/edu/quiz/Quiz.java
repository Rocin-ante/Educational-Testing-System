package edu.quiz;

import java.util.ArrayList;
import java.util.List;

/**
 * Quiz is responsible for the entire testing process:
 * - Maintain a list of questions
 * - Control the index of the current topic
 * - Calculate score
 * - Collect incorrect questions
 */
public class Quiz {

    private final List<Question> questions = new ArrayList<>();
    private int currentIndex = 0;

    /** Add a question to the test */
    public void addQuestion(Question q) {
        if (q != null) {
            questions.add(q);
        }
    }

    /** Get the current question */
    public Question getCurrentQuestion() {
        if (questions.isEmpty()) {
            throw new IllegalStateException("The list of questions is empty, unable to retrieve the current question.");
        }
        return questions.get(currentIndex);
    }

    /**
     * Jump to the next question.
     * If successfully moved to the next question, return true; If it is already the last question, return false.
     */
    public boolean moveToNextQuestion() {
        if (currentIndex < questions.size() - 1) {
            currentIndex++;
            return true;
        }
        return false;
    }

    /**
     * Calculate the score based on the student answer list.
     * Assuming that the order of answers is consistent with the order of the questions.
     */
    public int calculateScore(List<String> answers) {
        int score = 0;

        for (int i = 0; i < answers.size() && i < questions.size(); i++) {
            Question q = questions.get(i);
            if (q.isCorrect(answers.get(i))) {
                score++;
            }
        }
        return score;
    }

    /** Return a list of all incorrectly answered questions for generating detailed feedback. */
    public List<Question> getIncorrectQuestions(List<String> answers) {
        List<Question> wrong = new ArrayList<>();

        for (int i = 0; i < answers.size() && i < questions.size(); i++) {
            Question q = questions.get(i);
            if (!q.isCorrect(answers.get(i))) {
                wrong.add(q);
            }
        }
        return wrong;
    }

    /**
     * Return the student's answers corresponding to the incorrect questions.
     * The order matches getIncorrectQuestions(...).
     */
    public List<String> getIncorrectStudentAnswers(List<String> answers) {
        List<String> result = new ArrayList<>();

        if (answers == null) {
            return result;
        }

        for (int i = 0; i < answers.size() && i < questions.size(); i++) {
            Question q = questions.get(i);
            String answer = answers.get(i);
            if (!q.isCorrect(answer)) {
                result.add(answer);
            }
        }
        return result;
    }

    public int getTotalQuestions() {
        return questions.size();
    }
    public void loadApiQuestions(int count) {
        QuestionFactory factory = new QuestionFactory();
        ApiQuestionLoader loader = new ApiQuestionLoader(factory);
        List<Question> apiQs = loader.fetchApiQuestions(count);
        questions.addAll(apiQs);

        System.out.println("Loaded " + apiQs.size() + " API questions into Quiz.");
    }

}
