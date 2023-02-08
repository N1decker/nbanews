package ru.nidecker.nbanews.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nidecker.nbanews.entity.News;
import ru.nidecker.nbanews.entity.NewsSource;
import ru.nidecker.nbanews.entity.Role;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.repository.NewsRepository;
import ru.nidecker.nbanews.repository.NewsSourceLogoRepository;
import ru.nidecker.nbanews.util.validation.URLValidator;

import java.util.Arrays;

@Service
@Slf4j
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsSourceLogoRepository newsSourceLogoRepository;

    public NewsService(NewsRepository newsRepository, NewsSourceLogoRepository newsSourceLogoRepository) {
        this.newsRepository = newsRepository;
        this.newsSourceLogoRepository = newsSourceLogoRepository;
    }

    @SneakyThrows
    public News save(String title,
                     String image,
                     String sourceURL,
                     String newsSource,
                     User user) {
        log.info(Arrays.toString(image.getBytes()));
        if (!user.getRoles().contains(Role.EDITOR)) {
            throw new IllegalArgumentException("You don't have privileges");
        } else {
            if (!URLValidator.validate(sourceURL)) {
                throw new IllegalArgumentException("incorrect link to the source");
            }
            News news = new News();
            try {
//                news.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
                news.setImageURL(image);
                news.setNewsSource(chooseNewsSource(newsSource));
            } catch (Exception ignored) {
            }
            news.setContentAuthor(user.getNickname());
            news.setTitle(title);
            news.setSource(sourceURL);
            return newsRepository.save(news);
        }
    }

    private NewsSource chooseNewsSource(String newsSourceName) {
        return newsSourceLogoRepository.findNewsSourceByNameIgnoreCase(newsSourceName);
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