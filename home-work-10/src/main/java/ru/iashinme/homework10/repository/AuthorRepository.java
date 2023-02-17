package ru.iashinme.homework10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.homework10.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
