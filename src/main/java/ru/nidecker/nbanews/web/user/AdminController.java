package ru.nidecker.nbanews.web.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.repository.UserRepository;

@Controller
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final UserRepository userRepository;

    @GetMapping
    public String users(Model model,
                        @AuthenticationPrincipal User user) {
        log.info("go to users page");
        model.addAttribute("user",userRepository.findByEmail(user.getEmail()).orElseThrow());
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }
}
