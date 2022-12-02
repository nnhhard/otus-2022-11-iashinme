package ru.iashinme.domain;

public class Answer {
    private String answer;
    private boolean isTrue;

    public Answer(String answer, boolean isTrue) {
        this.answer = answer;
        this.isTrue = isTrue;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setIsTrue(boolean isTrue) {
        this.isTrue = isTrue;
    }

    public String getAnswer() {
        return answer;
    }

    public Boolean getIsTrue() {
        return isTrue;
    }
}
