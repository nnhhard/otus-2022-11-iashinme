package ru.iashinme.homework12.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.homework12.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
