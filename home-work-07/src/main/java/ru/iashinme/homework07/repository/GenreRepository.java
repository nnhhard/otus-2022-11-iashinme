package ru.iashinme.homework07.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.homework07.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
