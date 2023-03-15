package ru.iashinme.homework13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.homework13.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
