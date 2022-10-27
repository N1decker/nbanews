package ru.nidecker.nbanews.controller.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.repository.UserRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class RestProfileController {

    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity changeProfile(@AuthenticationPrincipal User user) {
        return null;
    }
}
