package ru.nidecker.nbanews.controller.news.comment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.nidecker.nbanews.entity.Comment;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.repository.NewsRepository;
import ru.nidecker.nbanews.repository.CommentRepository;

import java.util.List;

@RestController
@RequestMapping("/news")
@Slf4j
public class RestCommentsController {
    private final NewsRepository newsRepository;
    private final CommentRepository newsUserRelationshipRepository;

    public RestCommentsController(NewsRepository newsRepository, CommentRepository newsUserRelationshipRepository) {
        this.newsRepository = newsRepository;
        this.newsUserRelationshipRepository = newsUserRelationshipRepository;
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getAll(@PathVariable long id) {
        log.info("get comments by newsId=" + id);
        return newsUserRelationshipRepository.findAllByNewsId(id);
    }

    @PostMapping("/{id}/comments")
    @Transactional
    public void addComment(@AuthenticationPrincipal User user,
                           @RequestParam String message,
                           @PathVariable long id) {
        log.info(String.format("add comment to news with id = %d by user %s", id, user));
        Comment comment = new Comment();
        comment.setComment(message);
        comment.setNews(newsRepository.getReferenceById(id));
        comment.setUser(user);
        newsUserRelationshipRepository.save(comment);
    }
}
