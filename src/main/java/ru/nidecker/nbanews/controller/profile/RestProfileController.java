package ru.nidecker.nbanews.controller.profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.exception.FieldAlreadyTakenException;
import ru.nidecker.nbanews.exception.WrongPasswordException;
import ru.nidecker.nbanews.repository.UserRepository;
import ru.nidecker.nbanews.util.validation.EmailValidator;
import ru.nidecker.nbanews.util.validation.PasswordValidator;

import javax.transaction.Transactional;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
@Slf4j
public class RestProfileController {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping
    @Transactional
    public void changeProfileFields(@AuthenticationPrincipal User user,
                                    @RequestParam("inputName") String inputName,
                                    @RequestParam("changeTo") String changeTo) {
        log.info(String.format("change field %s to %s by user %s", inputName, changeTo, user));
        switch (inputName) {
            case "nickname" -> {
                User byNickname = userRepository.findByNickname(changeTo).orElse(null);
                if (byNickname == null) {
                    User changeNickname = userRepository.findById(user.getId()).orElseThrow();
                    changeNickname.setNickname(changeTo);
                    userRepository.save(changeNickname);
                } else if (!byNickname.getId().equals(user.getId())) {
                    throw new FieldAlreadyTakenException(String.format("This %s already taken", inputName));
                }
            }
            case "email" -> {
                User byEmail = userRepository.findByEmail(changeTo).orElse(null);
                if (byEmail == null) {
                    boolean isValidEmail = EmailValidator.validate(changeTo);
                    if (!isValidEmail)
                        throw new IllegalStateException("email not valid");

                    User changeEmail = userRepository.findById(user.getId()).orElseThrow();
                    changeEmail.setEmail(changeTo);
                    userRepository.save(changeEmail);
                } else if (!byEmail.getId().equals(user.getId())) {
                    throw new FieldAlreadyTakenException(String.format("This %s already taken", inputName));
                }
            }
        }
    }

    @Transactional
    @PostMapping("/change-password")
    public void changePassword(@AuthenticationPrincipal User auth,
                               @RequestParam("oldPassword") String oldPassword,
                               @RequestParam("newPassword") String newPassword) {
        log.info(String.format("change password by user %s", auth));
        User user = userRepository.findById(auth.getId()).orElseThrow();
        if (!bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            throw new WrongPasswordException("wrong old password");
        }
        String invalidPasswordMessage = PasswordValidator.isValidPassword(newPassword);
        if (invalidPasswordMessage != null) {
            throw new WrongPasswordException(invalidPasswordMessage);
        }
        userRepository.changePassword(bCryptPasswordEncoder.encode(newPassword), user.getEmail());
    }

    @PostMapping("/change-avatar")
    public void changeAvatar(@AuthenticationPrincipal User auth,
                             @RequestParam("avatar") MultipartFile avatar) {
        log.info(String.format("change avatar by user %s", auth));
        User user = userRepository.findById(auth.getId()).orElseThrow();
        try {
            user.setAvatar(Base64.getEncoder().encodeToString(avatar.getBytes()));
        } catch (Exception ignored) {
        }
        userRepository.save(user);
    }
}