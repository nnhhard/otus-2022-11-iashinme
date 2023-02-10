package ru.iashinme.homework09.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.homework09.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
