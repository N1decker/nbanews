package ru.nidecker.nbanews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nidecker.nbanews.entity.User;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.enabled = true WHERE u.email = :email")
    void enableUser(String email);

    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.email = :email")
    void changePassword(String password, String email);

    @Transactional
    @Modifying
    void deleteUsersByIdIn(List<Long> ids);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.locked = :locked where u.email = :email")
    void blockUser(String email, boolean locked);
}