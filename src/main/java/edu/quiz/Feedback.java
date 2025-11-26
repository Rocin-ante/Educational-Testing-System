package edu.quiz;

import java.util.ArrayList;
import java.util.List;

/**
 * Feedback generates different levels of feedback based on scores and a list of incorrect answers:
 * - Simple score feedback
 * - Detailed incorrect answer feedback
 * - Performance level
 *
 * Now it also supports showing the student's answer next to the correct answer.
 */
public class Feedback {

    private final int score;
    private final int total;
    private final List<Question> incorrectQuestions;
    private final List<String> incorrectStudentAnswers; // student's answers for the incorrect questions

    /**
     * Original constructor kept for compatibility (e.g. unit tests).
     * Student answers are not provided in this version.
     */
    public Feedback(int score, int total, List<Question> incorrectQuestions) {
        this(score, total, incorrectQuestions, new ArrayList<>());
    }

    /**
     * New constructor that also receives the student's answers
     * corresponding to each incorrect question.
     */
    public Feedback(int score,
                    int total,
                    List<Question> incorrectQuestions,
                    List<String> incorrectStudentAnswers) {
        this.score = score;
        this.total = total;
        this.incorrectQuestions = incorrectQuestions;
        this.incorrectStudentAnswers = incorrectStudentAnswers;
    }

    /** Brief score feedback */
    public String generateScoreFeedback() {
        return "Your score: " + score + " / " + total;
    }

    /** Detailed feedback on incorrect answers, including student's answer */
    public String generateDetailedFeedback() {
        if (incorrectQuestions == null || incorrectQuestions.isEmpty()) {
            return "Congratulations, you got them all right!";
        }

        StringBuilder sb = new StringBuilder("Detailed feedback on incorrect answers:\n\n");

        for (int i = 0; i < incorrectQuestions.size(); i++) {
            Question q = incorrectQuestions.get(i);

            // Use formatted question so that MC questions include options
            sb.append("Question:\n")
                    .append(q.getFormattedQuestion())
                    .append("\n");

            String studentAnswer = "(not recorded)";
            if (incorrectStudentAnswers != null && i < incorrectStudentAnswers.size()) {
                studentAnswer = incorrectStudentAnswers.get(i);
            }

            sb.append("Your answer: ").append(studentAnswer).append("\n");
            sb.append("Correct answer: ").append(q.getCorrectAnswer()).append("\n\n");
        }
        return sb.toString();
    }

    /** Performance levels are assigned based on the percentage of scores. */
    public String getPerformanceLevel() {
        if (total == 0) return "No results";

        double ratio = (double) score / total;
        if (ratio >= 0.9) return "A";
        if (ratio >= 0.75) return "B";
        if (ratio >= 0.6) return "C";
        return "F";
    }
}
