package ru.nidecker.nbanews.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.nidecker.nbanews.entity.News;
import ru.nidecker.nbanews.entity.Role;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.repository.NewsRepository;
import ru.nidecker.nbanews.util.validation.URLValidator;

import java.util.Arrays;
import java.util.Base64;

@Service
@Slf4j
public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @SneakyThrows
    public News save(String title,
                     MultipartFile image,
                     String source,
                     MultipartFile sourceLogo,
                     User user) {
        log.info(Arrays.toString(image.getBytes()));
        if (!user.getRoles().contains(Role.EDITOR)) {
            throw new IllegalArgumentException("You don't have privileges");
        } else {
            if (!URLValidator.validate(source)) {
                throw new IllegalArgumentException("incorrect link to the source");
            }
            News news = new News();
            try {
                news.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
                news.setSourceLogo(Base64.getEncoder().encodeToString(sourceLogo.getBytes()));
            } catch (Exception ignored) {
            }
            news.setEditor(user.getNickname());
            news.setTitle(title);
            news.setSource(source);
            return newsRepository.save(news);
        }
    }

    public void delete(long id,
                       User user) {
        log.info("attempt to delete news with id {} by user {}", id, user);
        if (!user.getRoles().contains(Role.EDITOR)) {
            throw new IllegalArgumentException("You don't have privileges");
        } else {
            newsRepository.deleteById(id);
        }
    }
}