package ru.iashinme.homework12.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.homework12.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
