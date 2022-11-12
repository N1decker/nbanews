package ru.nidecker.nbanews.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.nidecker.nbanews.entity.News;
import ru.nidecker.nbanews.entity.Role;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.repository.NewsRepository;

import java.util.Base64;
import java.util.Objects;

@Service
@Slf4j
public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public News save(String title, MultipartFile image, String source, MultipartFile sourceLogo, User user) {
        if (!user.getRoles().contains(Role.EDITOR)) {
            throw new IllegalStateException("You don't have privileges");
        } else {
            News news = new News();
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
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

    public void delete(long id, User user) {
        log.info("attempt to delete news with id {} by user {}", id, user);
        if (!user.getRoles().contains(Role.EDITOR)) {
            throw new IllegalStateException("You don't have privileges");
        } else {
            newsRepository.deleteById(id);
        }
    }
}