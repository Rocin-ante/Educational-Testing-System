package edu.quiz;

import java.util.List;

/**
 * Feedback generates different levels of feedback based on scores and a list of incorrect answers:
 * - Simple score feedback
 * - Detailed incorrect answer feedback
 * - Performance level
 */
public class Feedback {

    private final int score;
    private final int total;
    private final List<Question> incorrectQuestions;

    public Feedback(int score, int total, List<Question> incorrectQuestions) {
        this.score = score;
        this.total = total;
        this.incorrectQuestions = incorrectQuestions;
    }

    /** Brief score feedback */
    public String generateScoreFeedback() {
        return "Your score：" + score + " / " + total;
    }

    /** Detailed feedback on incorrect answers */
    public String generateDetailedFeedback() {
        if (incorrectQuestions == null || incorrectQuestions.isEmpty()) {
            return "Congratulations, you got them all right!";
        }

        StringBuilder sb = new StringBuilder("Detailed feedback on incorrect answers：\n\n");
        for (Question q : incorrectQuestions) {
            sb.append("Question：").append(q.getQuestionText())
                    .append("\nCorrect answer：").append(q.getCorrectAnswer())
                    .append("\n\n");
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
