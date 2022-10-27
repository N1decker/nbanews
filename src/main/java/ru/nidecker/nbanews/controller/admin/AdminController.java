package ru.nidecker.nbanews.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nidecker.nbanews.repository.UserRepository;

@Controller
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    @GetMapping
    public String users(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users";
    }
}
