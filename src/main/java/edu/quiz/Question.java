package edu.quiz;

/**
 * An abstract question class that defines the common structure and behavior of all questions.
 * Subclasses must implement the specific logic for answer judgment and question display.
 */
public abstract class Question {

    /** Question content */
    protected String questionText;

    /** The correct answer (can be the option key or a plain text answer) */
    protected String correctAnswer;

    public Question(String questionText, String correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    /**
     * Determine if a given answer is correct.
     * @param answer The student's answer
     * @return Whether it is correct
     */
    public abstract boolean isCorrect(String answer);

    /**
     * Returns a formatted question string for display in the console or GUI.
     */
    public abstract String getFormattedQuestion();

    public String getQuestionText() {
        return questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Checks if this question came from an API.
     * Default is false.
     */
    public boolean isApiQuestion() {
        return false;
    }
}