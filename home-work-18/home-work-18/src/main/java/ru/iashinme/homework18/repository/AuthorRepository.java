package ru.iashinme.homework18.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.homework18.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
