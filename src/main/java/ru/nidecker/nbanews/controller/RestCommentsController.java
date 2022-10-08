package ru.nidecker.nbanews.controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.nidecker.nbanews.entity.News_User_Relationship;
import ru.nidecker.nbanews.repository.NewsRepository;
import ru.nidecker.nbanews.repository.News_User_RelationshipRepository;

import java.util.List;

@RestController
@RequestMapping("/news")
public class RestCommentsController {

    private final NewsRepository newsRepository;
    private final News_User_RelationshipRepository newsUserRelationshipRepository;

    public RestCommentsController(NewsRepository newsRepository, News_User_RelationshipRepository newsUserRelationshipRepository) {
        this.newsRepository = newsRepository;
        this.newsUserRelationshipRepository = newsUserRelationshipRepository;
    }

    @GetMapping("/{id}/comments")
    public List<News_User_Relationship> getAll(@PathVariable long id){
        return newsUserRelationshipRepository.findAllByNewsId(id);
    }

    @PostMapping("/{id}/comments")
    @Transactional
    public void addComment(@RequestParam String message, @PathVariable long id) {
        News_User_Relationship comment = new News_User_Relationship();
        comment.setComment(message);
        comment.setNews(newsRepository.getReferenceById(id));
        newsUserRelationshipRepository.save(comment);
    }
}
