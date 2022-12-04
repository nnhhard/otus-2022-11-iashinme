package ru.iashinme.domain;

public class Student {
    private String surname;
    private String name;

    public Student (String surname, String name) {
        this.surname = surname;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
