package edu.quiz;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Swing GUI for the Quiz System.
 * - Uses LocalQuestionLoader to load questions from CSV
 * - Has three states: WELCOME, IN_PROGRESS, COMPLETED
 * - Shows feedback in the main window with scrolling
 * - Light styling: Nimbus Look & Feel, unified fonts, soft background
 */
public class QuizGuiApp extends JFrame {

    private Quiz quiz;

    // UI components
    private JLabel statusLabel;
    private JTextArea questionArea;
    private JTextField answerField;
    private JButton actionButton;

    private JTextArea feedbackArea;
    private JScrollPane questionScroll;
    private JScrollPane feedbackScroll;
    private JPanel centerPanel;
    private JPanel answerPanel;

    // state
    private final List<String> studentAnswers = new ArrayList<>();
    private boolean started = false;
    private boolean finished = false;

    public QuizGuiApp() {
        initLookAndFeel();   // 先应用外观和字体
        initCoreLogic();
        initGui();
        showWelcome();
    }

    /**
     * Apply Nimbus Look & Feel and unify default fonts.
     */
    private void initLookAndFeel() {
        try {
            // Use Nimbus if available
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            Font uiFont = new Font("Segoe UI", Font.PLAIN, 14);
            UIManager.put("Label.font", uiFont);
            UIManager.put("Button.font", uiFont);
            UIManager.put("TextField.font", uiFont);
            UIManager.put("TextArea.font", uiFont);
        } catch (Exception e) {
            System.out.println("Nimbus look and feel not available, using default.");
        }
    }

    /**
     * Load questions using LocalQuestionLoader.
     */
    private void initCoreLogic() {
        QuestionFactory factory = new QuestionFactory();
        LocalQuestionLoader loader = new LocalQuestionLoader(factory);

        quiz = new Quiz();

        // Load local questions (CSV)
        List<Question> localQuestions = loader.loadMixedQuestions(8, 5, 3);
        for (Question q : localQuestions) {
            quiz.addQuestion(q);
        }

        // ------------------------------
        // 🔥 Load API questions (NEW)
        // ------------------------------
        ApiQuestionLoader apiLoader = new ApiQuestionLoader(factory);

        // Fetch 3 API questions
        List<Question> apiQuestions = apiLoader.fetchApiQuestions(3);

        // Add them into the quiz
        for (Question q : apiQuestions) {
            quiz.addQuestion(q);


        }
        int apiCount = apiQuestions.size();
        JLabel apiInfo = new JLabel("Loaded " + apiCount + " API questions");

    }

    /**
     * Build a lightly styled GUI.
     */
    private void initGui() {
        setTitle("Quiz System - Swing GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        // overall background
        getContentPane().setBackground(new Color(245, 247, 250));

        // top: status label
        statusLabel = new JLabel("Quiz System", SwingConstants.CENTER);
        statusLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        statusLabel.setFont(statusLabel.getFont().deriveFont(Font.BOLD, 16f));
        add(statusLabel, BorderLayout.NORTH);

        // center: a "card" panel that will host question OR feedback
        centerPanel = new JPanel(new BorderLayout(8, 8));
        centerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        centerPanel.setBackground(new Color(250, 250, 252));
        add(centerPanel, BorderLayout.CENTER);

        // question area (used in welcome + in-progress)
        questionArea = new JTextArea();
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setMargin(new Insets(8, 8, 8, 8));

        questionScroll = new JScrollPane(questionArea);
        questionScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 230)),
                "Question"
        ));

        // feedback area (used in completed state)
        feedbackArea = new JTextArea();
        feedbackArea.setEditable(false);
        feedbackArea.setLineWrap(true);
        feedbackArea.setWrapStyleWord(true);
        feedbackArea.setMargin(new Insets(8, 8, 8, 8));

        feedbackScroll = new JScrollPane(feedbackArea);
        feedbackScroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 230)),
                "Quiz Feedback"
        ));

        // bottom: answer field + button, in a light bar
        answerPanel = new JPanel(new BorderLayout(5, 5));
        answerPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        answerPanel.setBackground(new Color(245, 247, 250));

        JLabel answerLabel = new JLabel("Your Answer:");
        answerField = new JTextField();
        answerField.setPreferredSize(new Dimension(200, 30));
        answerPanel.add(answerLabel, BorderLayout.WEST);
        answerPanel.add(answerField, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setBorder(new EmptyBorder(5, 15, 10, 15));
        bottomPanel.setBackground(new Color(245, 247, 250));
        bottomPanel.add(answerPanel, BorderLayout.CENTER);

        actionButton = new JButton("Start Quiz");
        actionButton.setFocusPainted(false);
        actionButton.setPreferredSize(new Dimension(140, 35));
        actionButton.addActionListener(e -> onActionButtonClicked());
        bottomPanel.add(actionButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Show welcome text before the quiz starts.
     */
    private void showWelcome() {
        centerPanel.removeAll();
        centerPanel.add(questionScroll, BorderLayout.CENTER);

        questionArea.setText(
                "Welcome to the quiz!\n\n" +
                        "You will answer a set of questions.\n" +
                        "- Some are simple text questions.\n" +
                        "- Some are multiple-choice.\n\n" +
                        "Click \"Start Quiz\" to begin."
        );

        answerField.setText("");
        answerField.setEditable(false);   // cannot type before start
        answerPanel.setVisible(false);    // hide input row

        statusLabel.setText("Ready to start");
        actionButton.setText("Start Quiz");

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    /**
     * Show the current question during the quiz.
     */
    private void showCurrentQuestion() {
        centerPanel.removeAll();
        centerPanel.add(questionScroll, BorderLayout.CENTER);

        Question current = quiz.getCurrentQuestion();
        questionArea.setText(current.getFormattedQuestion());

        answerField.setText("");
        answerField.setEditable(true);
        answerPanel.setVisible(true);

        int index = studentAnswers.size() + 1;
        int total = quiz.getTotalQuestions();
        statusLabel.setText("Question " + index + " of " + total);
        actionButton.setText("Submit & Next");

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    /**
     * Handle button based on current state.
     */
    private void onActionButtonClicked() {
        // first click: start quiz
        if (!started) {
            started = true;
            showCurrentQuestion();
            return;
        }

        // after finish: exit
        if (finished) {
            System.exit(0);
            return;
        }

        // normal answering
        String answer = answerField.getText().trim();
        if (answer.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter an answer before continuing.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        studentAnswers.add(answer);

        if (quiz.moveToNextQuestion()) {
            showCurrentQuestion();
        } else {
            showFeedback();
        }
    }

    /**
     * After last question, show feedback instead of question.
     */
    private void showFeedback() {
        int score = quiz.calculateScore(studentAnswers);
        List<Question> wrong = quiz.getIncorrectQuestions(studentAnswers);
        List<String> wrongAnswers = quiz.getIncorrectStudentAnswers(studentAnswers);

        // A/B/C/F grading
        String grade;
        double percentage = (double) score / quiz.getTotalQuestions();
        if (percentage >= 0.90) grade = "A";
        else if (percentage >= 0.75) grade = "B";
        else if (percentage >= 0.60) grade = "C";
        else grade = "F";

        Feedback feedback = new Feedback(score, quiz.getTotalQuestions(), wrong, wrongAnswers);

        StringBuilder sb = new StringBuilder();
        sb.append("Score: ").append(score).append("/").append(quiz.getTotalQuestions()).append("\n");
        sb.append("Grade: ").append(grade).append("\n\n");

        // normalize line breaks
        String detailed = feedback.generateDetailedFeedback()
                .replace("\r\n", "\n")
                .replace("\r", "\n");
        sb.append(detailed);

        feedbackArea.setText(sb.toString());
        feedbackArea.setCaretPosition(0); // scroll to top

        // center shows feedback instead of question
        centerPanel.removeAll();
        centerPanel.add(feedbackScroll, BorderLayout.CENTER);

        // hide answer row
        answerField.setText("");
        answerField.setEditable(false);
        answerPanel.setVisible(false);

        statusLabel.setText("Quiz Completed!");
        actionButton.setText("Exit");
        finished = true;

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuizGuiApp app = new QuizGuiApp();
            app.setVisible(true);
        });
    }

}
