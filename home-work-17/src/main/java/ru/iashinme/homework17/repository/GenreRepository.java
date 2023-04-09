package ru.iashinme.homework17.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.homework17.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
