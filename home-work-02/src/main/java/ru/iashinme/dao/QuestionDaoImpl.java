package ru.iashinme.dao;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.iashinme.config.AppSettingCsvPathProvider;
import ru.iashinme.domain.Answer;
import ru.iashinme.domain.Question;
import java.io.*;
import java.util.*;

@Component
public class QuestionDaoImpl implements QuestionDao {

    private final static int INDEX_COLUMN_WITH_QUESTION_IN_ROW = 0;
    private final static int INDEX_COLUMN_WITH_ANSWER_AFTER_QUESTION = 0;
    private final static int INDEX_COLUMN_WITH_ANSWER_CORRECT_AFTER_QUESTION = 1;
    private final AppSettingCsvPathProvider appSettingCsvPathProvider;

    public QuestionDaoImpl(AppSettingCsvPathProvider appSettingCsvPathProvider) {
        this.appSettingCsvPathProvider = appSettingCsvPathProvider;
    }

    @Override
    public List<Question> findAll() {
        return readFileResource();
    }

    private List<Question> readFileResource() {
        List<Question> questionList = new ArrayList<>();
        String resourceName = appSettingCsvPathProvider.getResourceName();
        if (resourceName != null) {
            inputStreamToCsv(getInputStreamInFileResource(resourceName))
                    .forEach(rowFileInFile -> {
                        if (!rowFileInFile[INDEX_COLUMN_WITH_QUESTION_IN_ROW].isEmpty()) {
                            questionList.add(getQuestionFromString(rowFileInFile));
                        }
                    }
            );
        }
        return questionList;
    }

    private Question getQuestionFromString(String[] stringArray) {
        String question = stringArray[INDEX_COLUMN_WITH_QUESTION_IN_ROW];
        List<Answer> answers = getAnswersListFromString(Arrays.copyOfRange(stringArray, 1, stringArray.length));
        return new Question(question, answers);
    }

    private List<Answer> getAnswersListFromString(String[] stringArray) {
        List<Answer> answers = new ArrayList<>();
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
        return answers;
    }

    private List<String[]> inputStreamToCsv(InputStream inputStream) {
        List<String[]> readerList;

        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            readerList = reader.readAll();
        } catch (Exception e) {
            throw new RuntimeException("Exception reading CSV file!");
        }

        return readerList == null ? Collections.emptyList() : readerList;
    }

    private InputStream getInputStreamInFileResource(String resourceName) {
        return this.getClass()
                .getClassLoader()
                .getResourceAsStream(resourceName);
    }
}
