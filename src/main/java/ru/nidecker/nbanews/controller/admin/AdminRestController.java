package ru.nidecker.nbanews.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.service.UserService;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class AdminRestController {

    private final UserService userService;

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteUser(@PathVariable long id,
                           @AuthenticationPrincipal User auth) {
        userService.deleteUser(id, auth);
    }

    @PostMapping("/{id}/block")
    @Transactional
    public void blockUser(@PathVariable long id,
                          @RequestParam("locked") boolean locked,
                          @AuthenticationPrincipal User auth) {
        userService.blockUser(id, locked, auth);
    }
}
