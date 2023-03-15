package ru.iashinme.homework13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.homework13.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName);
}
