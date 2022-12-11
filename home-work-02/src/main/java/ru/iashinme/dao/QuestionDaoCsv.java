package ru.iashinme.dao;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Component;
import ru.iashinme.config.ResourceNameProvider;
import ru.iashinme.domain.Answer;
import ru.iashinme.domain.Question;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Boolean.parseBoolean;

@Component
public class QuestionDaoCsv implements QuestionDao {

    private final static int INDEX_COLUMN_WITH_QUESTION_IN_ROW = 0;
    private final static int INDEX_COLUMN_WITH_ANSWER_AFTER_QUESTION = 0;
    private final static int INDEX_COLUMN_WITH_ANSWER_CORRECT_AFTER_QUESTION = 1;
    private final ResourceNameProvider resourceNameProvider;

    public QuestionDaoCsv(ResourceNameProvider resourceNameProvider) {
        this.resourceNameProvider = resourceNameProvider;
    }

    @Override
    public List<Question> findAll() {
        return readFileResource();
    }

    private List<Question> readFileResource() {
        String resourceName = resourceNameProvider.getResourceName();
        try {
            return Optional.of(resourceName)
                    .map(this::getInputStreamInFileResource)
                    .map(this::inputStreamToCsv)
                    .get()
                    .stream()
                    .filter(row -> !row[INDEX_COLUMN_WITH_QUESTION_IN_ROW].isEmpty())
                    .map(this::getQuestionFromString)
                    .collect(Collectors.toList());
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ReadCsvFileException(e);
        } catch (NoSuchElementException e) {
            throw new ReadCsvFileException("Exception reading CSV file!");
        }
    }

    private Question getQuestionFromString(String[] stringQuestion) {
        try {
            String question = stringQuestion[INDEX_COLUMN_WITH_QUESTION_IN_ROW];
            List<Answer> answers = getAnswersListFromString(Arrays.copyOfRange(stringQuestion, 1, stringQuestion.length));
            return new Question(question, answers);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ReadCsvFileException(e);
        }
    }

    private List<Answer> getAnswersListFromString(String[] stringAnswer) {
        try {
            return Arrays.stream(stringAnswer)
                    .map(row -> row.split(";"))
                    .map(rowAnswer -> {
                        var answerText = rowAnswer[INDEX_COLUMN_WITH_ANSWER_AFTER_QUESTION];
                        var isCorrect = rowAnswer.length > 1 && parseBoolean(rowAnswer[INDEX_COLUMN_WITH_ANSWER_CORRECT_AFTER_QUESTION]);
                        return new Answer(answerText, isCorrect);
                    }).collect(Collectors.toList());
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ReadCsvFileException(e);
        }
    }

    private List<String[]> inputStreamToCsv(InputStream inputStream) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            return reader.readAll();
        } catch (Exception e) {
            throw new ReadCsvFileException(e);
        }
    }

    private InputStream getInputStreamInFileResource(String resourceName) {
        return this.getClass()
                .getClassLoader()
                .getResourceAsStream(resourceName);
    }
}
