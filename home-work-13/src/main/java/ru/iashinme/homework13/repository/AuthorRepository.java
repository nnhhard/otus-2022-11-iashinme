package ru.iashinme.homework13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.homework13.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
