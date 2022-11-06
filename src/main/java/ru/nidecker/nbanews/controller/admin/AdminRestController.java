package ru.nidecker.nbanews.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminRestController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAll() {
        log.info("get all users");
        return userRepository.findAll();
    }

    @DeleteMapping("/users/{id}")
    @Transactional
    public void deleteUser(@PathVariable long id) {
        log.info(String.format("delete user with id = %d", id));
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
    }
}
