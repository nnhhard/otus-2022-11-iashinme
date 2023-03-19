package ru.iashinme.homework14.repository.h2;

import org.springframework.data.repository.CrudRepository;
import ru.iashinme.homework14.model.h2.BookSQL;

import java.util.List;

public interface BookRepository extends CrudRepository<BookSQL, Long> {

    List<BookSQL> findAll();
}
