package ru.nidecker.nbanews.controller.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.exception.FieldAlreadyTakenException;
import ru.nidecker.nbanews.repository.UserRepository;
import ru.nidecker.nbanews.validation.EmailValidator;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class RestProfileController {

    private final UserRepository userRepository;

    @PostMapping
    @Transactional
    public void changeProfileFields(@AuthenticationPrincipal User user,
                                    @RequestParam("inputName") String inputName,
                                    @RequestParam("changeTo") String changeTo) {
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
                    EmailValidator.validate(changeTo);
                    User changeEmail = userRepository.findById(user.getId()).orElseThrow();
                    changeEmail.setEmail(changeTo);
                    userRepository.save(changeEmail);
                } else if (!byEmail.getId().equals(user.getId())) {
                    throw new FieldAlreadyTakenException(String.format("This %s already taken", inputName));
                }
            }
        }
    }
}
