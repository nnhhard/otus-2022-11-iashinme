package ru.iashinme.homework03.dao;

import ru.iashinme.homework03.domain.Question;

import java.util.List;


public interface QuestionDao {
    List<Question> findAll();
}
