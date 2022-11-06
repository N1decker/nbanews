package ru.nidecker.nbanews.registration.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c SET c.confirmedAt = :confirmedAt WHERE c.token = :token")
    int updateConfirmedAt(String token, LocalDateTime confirmedAt);

//    @Modifying
//    @Transactional
//    @Query(value = "" +
//            "delete from ConfirmationToken ct " +
//            "where ct.user.id in (:ids) " +
//            "and ct.expiresAt < current_timestamp " +
//            "and ct.confirmedAt is null")
//    void deleteConfirmationTokensByUserId(Set<Long> ids);

    @Query("select ct.user.id from ConfirmationToken ct " +
            "where date(ct.expiresAt) < current_date and ct.confirmedAt is null")
    List<Long> getUsersIdsFromConfirmationTokenTableWhereConfirmedAtIsNullAndExpiredAtLessThanNow();
}
