package ru.nidecker.nbanews.web.news.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.nidecker.nbanews.entity.Comment;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.repository.CommentRepository;
import ru.nidecker.nbanews.repository.NewsRepository;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class RestCommentsController {
    private final NewsRepository newsRepository;
    private final CommentRepository newsUserRelationshipRepository;

    @GetMapping("/{id}/comments")
    public List<Comment> getAll(@PathVariable long id) {
        log.info("get comments by newsId = {}", id);
        return newsUserRelationshipRepository.findAllByNewsId(id);
    }

    @Transactional
    @PostMapping("/{id}/comments")
    public String addComment(@AuthenticationPrincipal User user,
                             @RequestParam String message,
                             @PathVariable long id) {
        log.info("add comment to news with id = {} by user {}", id, user);
        Comment comment = new Comment();
        comment.setComment(message);
        comment.setNews(newsRepository.getReferenceById(id));
        comment.setUser(user);
        newsUserRelationshipRepository.save(comment);
        return user.getNickname();
    }
}
