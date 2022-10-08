package ru.nidecker.nbanews.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nidecker.nbanews.entity.News;
import ru.nidecker.nbanews.repository.NewsRepository;

import java.util.List;

@RestController
public class RestNewsController {

    private final NewsRepository newsRepository;

    public RestNewsController(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @GetMapping("/allnews")
    public List<News> news() {
        return newsRepository.findAllByOrderByIdDesc();
    }
}
