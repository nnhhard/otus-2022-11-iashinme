package ru.iashinme.homework12.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.homework12.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName);
}
