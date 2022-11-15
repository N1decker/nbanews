package ru.nidecker.nbanews.web.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.service.UserService;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
@Slf4j
public class RestProfileController {
    private final UserService userService;

    @PostMapping
    @Transactional
    public void changeProfileFields(@AuthenticationPrincipal User user,
                                    @RequestParam("inputName") String inputName,
                                    @RequestParam("changeTo") String changeTo) {
        userService.changeProfileFields(user, inputName, changeTo);
    }

    @Transactional
    @PostMapping("/change-password")
    public void changePassword(@AuthenticationPrincipal User auth,
                               @RequestParam("oldPassword") String oldPassword,
                               @RequestParam("newPassword") String newPassword) {
        userService.changePassword(auth, oldPassword, newPassword);
    }

    @PostMapping("/change-avatar")
    public void changeAvatar(@AuthenticationPrincipal User auth,
                             @RequestParam("avatar") MultipartFile avatar) {
        userService.changeAvatar(auth, avatar);
    }

    @DeleteMapping
    @Transactional
    public void deleteProfile(@AuthenticationPrincipal User user) {
        userService.deleteProfile(user);
    }
}