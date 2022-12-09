package ru.iashinme.dao;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Component;
import ru.iashinme.config.AppSettingResourceNameProvider;
import ru.iashinme.domain.Answer;
import ru.iashinme.domain.Question;
import java.io.*;
import java.util.*;

@Component
public class QuestionDaoImpl implements QuestionDao {

    private final static int INDEX_COLUMN_WITH_QUESTION_IN_ROW = 0;
    private final static int INDEX_COLUMN_WITH_ANSWER_AFTER_QUESTION = 0;
    private final static int INDEX_COLUMN_WITH_ANSWER_CORRECT_AFTER_QUESTION = 1;
    private final AppSettingResourceNameProvider appSettingResourceNameProvider;

    public QuestionDaoImpl(AppSettingResourceNameProvider appSettingResourceNameProvider) {
        this.appSettingResourceNameProvider = appSettingResourceNameProvider;
    }

    @Override
    public List<Question> findAll() {
        return readFileResource();
    }

    private List<Question> readFileResource() {
        List<Question> questionList = new ArrayList<>();
        String resourceName = appSettingResourceNameProvider.getResourceName();
        try {
            if (resourceName != null) {
                inputStreamToCsv(getInputStreamInFileResource(resourceName))
                        .forEach(rowFileInFile -> {
                                    if (!rowFileInFile[INDEX_COLUMN_WITH_QUESTION_IN_ROW].isEmpty()) {
                                        questionList.add(getQuestionFromString(rowFileInFile));
                                    }
                                }
                        );
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ReadCsvFileException();
        }

        return questionList;
    }

    private Question getQuestionFromString(String[] stringArray) {
        try {
            String question = stringArray[INDEX_COLUMN_WITH_QUESTION_IN_ROW];
            List<Answer> answers = getAnswersListFromString(Arrays.copyOfRange(stringArray, 1, stringArray.length));
            return new Question(question, answers);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ReadCsvFileException();
        }

    }

    private List<Answer> getAnswersListFromString(String[] stringArray) {
        List<Answer> answers = new ArrayList<>();
        try {
            for (String answerString : stringArray) {
                String[] answer = answerString.split(";");
                answers.add(
                        new Answer(
                                answer[INDEX_COLUMN_WITH_ANSWER_AFTER_QUESTION],
                                answer.length > 1
                                        && Boolean.parseBoolean(answer[INDEX_COLUMN_WITH_ANSWER_CORRECT_AFTER_QUESTION])
                        )
                );
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ReadCsvFileException();
        }
        return answers;
    }

    private List<String[]> inputStreamToCsv(InputStream inputStream) {
        List<String[]> readerList;

        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            readerList = reader.readAll();
        } catch (Exception e) {
            throw new ReadCsvFileException();
        }

        return readerList == null ? Collections.emptyList() : readerList;
    }

    private InputStream getInputStreamInFileResource(String resourceName) {
        return this.getClass()
                .getClassLoader()
                .getResourceAsStream(resourceName);
    }
}
