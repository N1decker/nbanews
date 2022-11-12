package ru.nidecker.nbanews.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.nidecker.nbanews.repository.ConfirmationTokenRepository;
import ru.nidecker.nbanews.repository.UserRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeleteConfirmationTokenEngine {

    private final UserRepository userRepository;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS, initialDelay = 1)
    @Async
    public void deletingUnconfirmedUsers() {
        log.info("deleting unconfirmed users");
        List<Long> unconfirmedUsersIds = confirmationTokenRepository.getUsersIdsFromConfirmationTokenTableWhereConfirmedAtIsNullAndExpiredAtLessThanNow();
        userRepository.deleteUsersByIdIn(unconfirmedUsersIds);
    }
}
