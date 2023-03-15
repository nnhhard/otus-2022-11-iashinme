package ru.iashinme.homework13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.homework13.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
