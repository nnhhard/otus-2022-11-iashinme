package ru.iashinme.homework07.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.homework07.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
