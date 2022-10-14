package ru.nidecker.nbanews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nidecker.nbanews.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}