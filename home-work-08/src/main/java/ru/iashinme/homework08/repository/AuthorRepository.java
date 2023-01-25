package ru.iashinme.homework08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.iashinme.homework08.model.Author;

import java.util.Optional;

public interface AuthorRepository extends MongoRepository<Author, String> {

    Optional<Author> findBySurnameAndNameAndPatronymic(String surname, String name, String patronymic);
}
