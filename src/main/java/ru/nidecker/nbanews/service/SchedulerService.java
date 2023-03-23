package ru.nidecker.nbanews.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nidecker.nbanews.repository.ImageRepository;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerService {

    private final ImageRepository imageRepository;

    @Transactional
    @Modifying
    @Scheduled(fixedRate = 86400, timeUnit = TimeUnit.SECONDS, initialDelay = 10)
    public void deleteUnusedImages() {
        imageRepository.deleteUnusedImages();
    }
}
