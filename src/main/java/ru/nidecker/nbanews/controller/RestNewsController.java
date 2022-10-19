package ru.nidecker.nbanews.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nidecker.nbanews.entity.LikeDislike;
import ru.nidecker.nbanews.entity.News;
import ru.nidecker.nbanews.repository.LikeDislikeRepository;
import ru.nidecker.nbanews.repository.NewsRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestNewsController {

    private final LikeDislikeRepository likeDislikeRepository;

    private final NewsRepository newsRepository;


    @GetMapping("/likes")
    public List<LikeDislike> likes() {
        return  likeDislikeRepository.findAll();
    }
}
