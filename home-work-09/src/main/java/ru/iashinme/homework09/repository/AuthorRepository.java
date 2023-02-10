package ru.iashinme.homework09.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.homework09.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
