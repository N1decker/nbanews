package ru.nidecker.nbanews.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.nidecker.nbanews.entity.News;
import ru.nidecker.nbanews.repository.NewsRepository;

import java.util.Base64;
import java.util.Objects;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public News save(String title, MultipartFile image, String source, MultipartFile sourceLogo) {
        News news = new News();
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
        try {
            news.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            news.setSourceLogo(Base64.getEncoder().encodeToString(sourceLogo.getBytes()));
        } catch (Exception ignored) {}

        news.setTitle(title);
        news.setSource(source);
        return newsRepository.save(news);
    }
}
