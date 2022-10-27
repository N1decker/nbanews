package ru.nidecker.nbanews.controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.nidecker.nbanews.entity.Comment;
import ru.nidecker.nbanews.repository.NewsRepository;
import ru.nidecker.nbanews.repository.CommentRepository;

import java.util.List;

@RestController
@RequestMapping("/news")
public class RestCommentsController {

    private final NewsRepository newsRepository;
    private final CommentRepository newsUserRelationshipRepository;

    public RestCommentsController(NewsRepository newsRepository, CommentRepository newsUserRelationshipRepository) {
        this.newsRepository = newsRepository;
        this.newsUserRelationshipRepository = newsUserRelationshipRepository;
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getAll(@PathVariable long id){
        return newsUserRelationshipRepository.findAllByNewsId(id);
    }

    @PostMapping("/{id}/comments")
    @Transactional
    public void addComment(@RequestParam String message, @PathVariable long id) {
        Comment comment = new Comment();
        comment.setComment(message);
        comment.setNews(newsRepository.getReferenceById(id));
        newsUserRelationshipRepository.save(comment);
    }
}
