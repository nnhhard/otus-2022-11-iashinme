package ru.iashinme.homework12.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.homework12.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
