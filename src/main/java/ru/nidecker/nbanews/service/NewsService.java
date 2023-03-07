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

import java.util.List;

import static ru.nidecker.nbanews.util.validation.URLValidator.validate;

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
                     String imageURL,
                     String sourceURL,
                     String newsSource,
                     User user) {

        if (!user.getRoles().contains(Role.EDITOR)) {
            throw new IllegalArgumentException("You don't have privileges");
        } else {
            if (!(validate(sourceURL))) {
                throw new IllegalArgumentException("incorrect link to the source");
            }
            if (!validate(imageURL)) {
                throw new IllegalArgumentException("incorrect link to the image");
            }
            News news = new News();
            try {
                news.setImageUrl(imageURL);
                news.setNewsSource(chooseNewsSource(newsSource));
            } catch (Exception ignored) {
            }
            news.setContentAuthor(user.getNickname());
            news.setTitle(title);
            news.setSource(sourceURL);
            return newsRepository.save(news);
        }
    }

    @SneakyThrows
    public News prepare(String title,
                        String subhead,
                        String sourceURL,
                        String imageURL,
                        NewsSource newsSource,
                        String contentAuthor) {

        News news = new News();
        news.setTitle(title);
        news.setSubhead(subhead);
        news.setSource(sourceURL);
        news.setImageUrl(imageURL);
        news.setNewsSource(newsSource);
        news.setContentAuthor(contentAuthor);

        return news;
    }


    public NewsSource chooseNewsSource(String newsSourceName) {
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

    public void saveAll(List<News> news) {
        newsRepository.saveAll(news);
    }
}