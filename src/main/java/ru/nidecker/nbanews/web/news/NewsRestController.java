package ru.nidecker.nbanews.web.news;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.service.NewsService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/news", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsRestController {

    private final NewsService newsService;

    @DeleteMapping("/{id}")
    public void deleteNews(@PathVariable long id,
                           @AuthenticationPrincipal User user) {
        newsService.delete(id, user);
    }
}