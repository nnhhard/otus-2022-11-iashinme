package ru.iashinme.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.blog.model.Technology;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {
}
