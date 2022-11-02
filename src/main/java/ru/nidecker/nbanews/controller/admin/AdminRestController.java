package ru.nidecker.nbanews.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.repository.CommentRepository;
import ru.nidecker.nbanews.repository.LikeDislikeRepository;
import ru.nidecker.nbanews.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminRestController {

    private final UserRepository userRepository;

    private final LikeDislikeRepository likeDislikeRepository;

    private final CommentRepository commentRepository;

    @GetMapping("/users")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @DeleteMapping("/users/{id}")
    @Transactional
    public void deleteUser(@PathVariable long id) {
        User user = userRepository.findById(id).orElseThrow();
//        likeDislikeRepository.deleteAllByUserId(id);
//        commentRepository.deleteAllByUserId(id);
        userRepository.delete(user);
    }
}
