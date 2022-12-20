package ru.iashinme.homework04.dao;

import ru.iashinme.homework04.domain.Question;

import java.util.List;


public interface QuestionDao {
    List<Question> findAll();
}
