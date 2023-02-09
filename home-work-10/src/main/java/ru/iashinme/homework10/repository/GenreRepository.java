package ru.iashinme.homework10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.homework10.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
