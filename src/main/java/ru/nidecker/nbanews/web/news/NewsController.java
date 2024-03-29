package ru.nidecker.nbanews.web.news;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nidecker.nbanews.entity.LikeDislike;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.repository.LikeDislikeRepository;
import ru.nidecker.nbanews.repository.NewsRepository;
import ru.nidecker.nbanews.repository.UserRepository;
import ru.nidecker.nbanews.service.NewsService;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/news", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class NewsController {
    private final LikeDislikeRepository likeDislikeRepository;

    private final NewsRepository newsRepository;

    private final NewsService newsService;

    private final UserRepository userRepository;

    @GetMapping
    public String news(Model model, @AuthenticationPrincipal User user) {
        log.info("go to news page by user {}", user.getNickname());
        model.addAttribute("user", userRepository.findByEmail(user.getEmail()).orElseThrow());
        model.addAttribute("news", newsRepository.findAllByOrderByIdDesc());
        Map<String, LikeDislike> likes = likeDislikeRepository.findAllByUserId(user.getId())
                .stream()
                .collect(Collectors.toMap(likeDislike -> (likeDislike.getNews().getId() + "" + likeDislike.getUser().getEmail()), likeDislike -> likeDislike));
        model.addAttribute("likesDislikes", likes);
        return "news";
    }

    @PostMapping
    public String save(@RequestParam("title") String title,
                       @RequestParam("image") String image,
                       @RequestParam("sourceURL") String sourceURL,
                       @RequestParam("sourceName") String sourceName,
                       @AuthenticationPrincipal User user) {
        newsService.save(title, image, sourceURL, sourceName, user);
        return "redirect:/news";
    }
}