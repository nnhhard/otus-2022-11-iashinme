package ru.iashinme.homework17.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.homework17.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
