package ru.nidecker.nbanews.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.nidecker.nbanews.entity.Role;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminRestController {

    private final UserRepository userRepository;

    @GetMapping
    public List<User> getAll() {
        log.info("get all users");
        return userRepository.findAll();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteUser(@PathVariable long id) {
        log.info("trying to delete user with id = {}", id);
        User user = userRepository.findById(id).orElseThrow();
        if (user.getNickname().equals("User") || user.getNickname().equals("Admin")) {
            throw new IllegalStateException("you cannot delete this user");
        } else {
            log.info("delete user with id = {}", id);
            userRepository.delete(user);
        }
    }

    @PostMapping("/{id}/block")
    public void blockUser(@PathVariable long id,
                          @RequestParam("locked") boolean locked) {
        log.info("trying to change the user's lock with id = {}", id);
        User user = userRepository.findById(id).orElseThrow();
        if (user.getNickname().equals("User") || user.getNickname().equals("Admin")) {
            throw new IllegalStateException("you cannot block this user");
        } else {
            log.info("change the user's lock with id = {}", id);
            userRepository.blockUser(user.getEmail(), locked);
        }
    }
}
