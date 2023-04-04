package ru.iashinme.homework16.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.iashinme.homework16.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query(value = "select count(*) from authors a " +
            "left join books b on b.author_id = a.id " +
            "where b.id is null",
            nativeQuery = true)
    Long countUnusedAuthors();
}
