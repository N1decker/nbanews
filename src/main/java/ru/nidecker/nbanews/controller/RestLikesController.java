package ru.nidecker.nbanews.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nidecker.nbanews.entity.LikeDislike;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.repository.LikeDislikeRepository;
import ru.nidecker.nbanews.repository.NewsRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
public class RestLikesController {

    private final LikeDislikeRepository likeDislikeRepository;

    private final NewsRepository newsRepository;

    @PostMapping("/changeLike")
    @Transactional
    public void changeLikeType(@AuthenticationPrincipal User user,
                               @RequestParam("likeType") int likeType,
                               @RequestParam("newsId") long newsId) {
        LikeDislike likeDislike = likeDislikeRepository.getByUserIdAndNewsId(user.getId(), newsId).orElse(new LikeDislike(newsRepository.getReferenceById(newsId), user));
        likeDislike.setLikeType(likeType);
        likeDislikeRepository.save(likeDislike);
    }
}
