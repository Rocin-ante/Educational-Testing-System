package edu.quiz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Loads questions from local CSV files in src/main/resources/questions.
 *
 * File 1: simple-questions.csv
 *   Columns: questionText,correctAnswer,hints
 *   - hints is a ";" separated list, e.g. "Basic math;Integer result"
 *
 * File 2: mc-questions.csv
 *   Columns: questionText,optionA,optionB,optionC,optionD,correctKey
 *   - correctKey is one of A,B,C,D
 */
public class LocalQuestionLoader {

    private static final String SIMPLE_FILE = "questions/simple-questions.csv";
    private static final String MC_FILE = "questions/mc-questions.csv";

    private final QuestionFactory factory;

    public LocalQuestionLoader(QuestionFactory factory) {
        this.factory = factory;
    }

    /**
     * Load up to maxCount simple questions from the CSV file.
     */
    public List<Question> loadSimpleQuestions(int maxCount) {
        List<Question> result = new ArrayList<>();

        try (BufferedReader reader = openResource(SIMPLE_FILE)) {
            String line;
            boolean isFirst = true;

            while ((line = reader.readLine()) != null) {
                // skip header
                if (isFirst) {
                    isFirst = false;
                    continue;
                }
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length < 3) {
                    // invalid line, skip
                    continue;
                }

                String questionText = parts[0].trim();
                String correctAnswer = parts[1].trim();
                String hintsRaw = parts[2].trim();

                String[] hints;
                if (hintsRaw.isEmpty()) {
                    hints = new String[0];
                } else {
                    hints = hintsRaw.split(";");
                    for (int i = 0; i < hints.length; i++) {
                        hints[i] = hints[i].trim();
                    }
                }

                Question q = factory.createSimpleQuestion(questionText, correctAnswer, hints);
                result.add(q);

                if (result.size() >= maxCount) {
                    break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load simple questions from " + SIMPLE_FILE, e);
        }

        return result;
    }

    /**
     * Load up to maxCount multiple choice questions from the CSV file.
     */
    public List<Question> loadMultipleChoiceQuestions(int maxCount) {
        List<Question> result = new ArrayList<>();

        try (BufferedReader reader = openResource(MC_FILE)) {
            String line;
            boolean isFirst = true;

            while ((line = reader.readLine()) != null) {
                // skip header
                if (isFirst) {
                    isFirst = false;
                    continue;
                }
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length < 6) {
                    // invalid line, skip
                    continue;
                }

                String questionText = parts[0].trim();
                String optionA = parts[1].trim();
                String optionB = parts[2].trim();
                String optionC = parts[3].trim();
                String optionD = parts[4].trim();
                String correctKey = parts[5].trim();

                Map<String, String> options = new LinkedHashMap<>();
                options.put("A", optionA);
                options.put("B", optionB);
                options.put("C", optionC);
                options.put("D", optionD);

                Question q = factory.createMultipleChoiceQuestion(
                        questionText, options, correctKey);
                result.add(q);

                if (result.size() >= maxCount) {
                    break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to load multiple-choice questions from " + MC_FILE, e);
        }

        return result;
    }

    /**
     * Load a mixed quiz: simple + MC questions.
     *
     * @param totalCount total number of questions
     * @param simpleCount how many simple questions to include
     * @param mcCount how many MC questions to include
     */
    public List<Question> loadMixedQuestions(int totalCount, int simpleCount, int mcCount) {
        List<Question> questions = new ArrayList<>();

        if (simpleCount > 0) {
            questions.addAll(loadSimpleQuestions(simpleCount));
        }
        if (mcCount > 0) {
            questions.addAll(loadMultipleChoiceQuestions(mcCount));
        }

        // shuffle to mix question types
        Collections.shuffle(questions);

        // trim to totalCount if needed
        if (questions.size() > totalCount) {
            return new ArrayList<>(questions.subList(0, totalCount));
        }

        return questions;
    }

    private BufferedReader openResource(String path) throws IOException {
        InputStream in = getClass().getClassLoader().getResourceAsStream(path);
        if (in == null) {
            throw new IOException("Resource not found: " + path);
        }
        return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    }
}
