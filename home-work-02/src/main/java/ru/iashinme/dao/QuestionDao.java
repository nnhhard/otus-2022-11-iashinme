package ru.iashinme.dao;


import ru.iashinme.domain.Question;
import java.util.List;

public interface QuestionDao {
    List<Question> findAll();
}
