package ru.iashinme.homework18.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.homework18.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
