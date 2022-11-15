package ru.nidecker.nbanews.web.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.repository.UserRepository;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
@Slf4j
public class ProfileController {
    private final UserRepository userRepository;

    @GetMapping("/settings")
    public String profileSettings(Model model,
                                  @AuthenticationPrincipal User user) {
        log.info("go to profile settings page by user {}", user);
        model.addAttribute("user", userRepository.findByEmail(user.getEmail()).orElseThrow());
        return "profileSettings";
    }
}