package ru.nidecker.nbanews.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.repository.UserRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
