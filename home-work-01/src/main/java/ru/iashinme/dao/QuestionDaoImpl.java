package ru.iashinme.dao;

import com.opencsv.CSVReader;
import ru.iashinme.domain.Answer;
import ru.iashinme.domain.Question;

import java.io.*;
import java.util.*;

public class QuestionDaoImpl implements QuestionDao {
    private final String resourceName;

    public QuestionDaoImpl(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public List<Question> findAll(){
        return readFileResource();
    }

    private List<Question> readFileResource() {
        List<Question> questionList = new ArrayList<>();

        if (resourceName != null) {
            List<String[]> allElementsFile = inputStreamToCsv(getInputStreamInFileResource(resourceName));
            for (String[] lineFile : allElementsFile) {
                if (lineFile[0].isEmpty()) continue;
                String question = lineFile[0];
                List<Answer> answers = new ArrayList<>();
                for (String row: Arrays.copyOfRange(lineFile, 1, lineFile.length)) {
                    String[] answer = row.split(";");
                    answers.add(new Answer(answer[0], answer.length > 1 ? Boolean.parseBoolean(answer[1]) : false));
                }

                questionList.add(new Question(
                        question,
                        answers
                ));
            }
        }
        return questionList;
    }

    private List<String[]> inputStreamToCsv(InputStream inputStream) {
        List<String[]> readerList = null;

        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            readerList = reader.readAll();
        } catch(Exception e) {
            throw new RuntimeException("Exception reading CSV file");
        }

        return readerList == null ? Collections.emptyList() : readerList;
    }

    private InputStream getInputStreamInFileResource(String resourceName) {
        return this.getClass()
                .getClassLoader()
                .getResourceAsStream(resourceName);
    }
}
