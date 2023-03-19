package ru.iashinme.homework14.repository.h2;

import org.springframework.data.repository.CrudRepository;
import ru.iashinme.homework14.model.h2.AuthorSQL;

import java.util.List;

public interface AuthorRepository extends CrudRepository<AuthorSQL, Long> {

    AuthorSQL findByMongoId(String mongoId);

    List<AuthorSQL> findAll();
}
