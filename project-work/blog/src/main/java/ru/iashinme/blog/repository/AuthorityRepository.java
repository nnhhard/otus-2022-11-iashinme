package ru.iashinme.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.blog.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByAuthority(String authority);
}