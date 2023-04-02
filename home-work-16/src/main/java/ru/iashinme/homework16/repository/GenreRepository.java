package ru.iashinme.homework16.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.homework16.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
