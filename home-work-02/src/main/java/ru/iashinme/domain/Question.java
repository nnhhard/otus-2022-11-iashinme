package ru.iashinme.domain;

import org.springframework.lang.NonNull;
import java.util.List;

public class Question {
    private String question;
    private List<Answer> answers;

    public Question(@NonNull String question, @NonNull List<Answer> answers) {
        this.answers = List.copyOf(answers);
        this.question = question;
    }

    public void setQuestion(@NonNull String question) {
        this.question = question;
    }

    public void setAnswers(@NonNull List<Answer> answers) {
        this.answers = List.copyOf(answers);
    }

    public String getQuestion() {
        return question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
