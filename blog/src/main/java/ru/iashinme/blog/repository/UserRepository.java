package ru.iashinme.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.blog.model.User;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}