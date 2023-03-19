package ru.iashinme.homework14.repository.h2;

import org.springframework.data.repository.CrudRepository;
import ru.iashinme.homework14.model.h2.GenreSQL;

import java.util.List;

public interface GenreRepository extends CrudRepository<GenreSQL, Long> {

    GenreSQL findByMongoId(String id);

    @Override
    List<GenreSQL> findAll();
}
