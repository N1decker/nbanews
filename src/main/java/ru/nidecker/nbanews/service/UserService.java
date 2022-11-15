package ru.nidecker.nbanews.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.nidecker.nbanews.entity.Role;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.exception.FieldAlreadyTakenException;
import ru.nidecker.nbanews.exception.WrongPasswordException;
import ru.nidecker.nbanews.entity.ConfirmationToken;
import ru.nidecker.nbanews.repository.UserRepository;
import ru.nidecker.nbanews.util.validation.EmailValidator;
import ru.nidecker.nbanews.util.validation.PasswordValidator;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MESSAGE = "User with email %s not found";
    private final UserRepository userRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MESSAGE, email)));
    }

    @Transactional
    public String signUpUser(User user) {
        boolean userExists = userRepository.findByEmail(user.getEmail())
                .isPresent();
        if (userExists) throw new IllegalArgumentException("email already taken");

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        confirmationTokenService.save(confirmationToken);
        return token;
    }

    public void enableUser(String email) {
        userRepository.enableUser(email);
    }

    @Transactional
    public void deleteUser(long id,
                           User auth) {
        if (!auth.getRoles().contains(Role.ADMIN)) {
            throw new IllegalStateException("You don't have privileges");
        } else {
            log.info("attempt to delete user with id = {}", id);
            User user = userRepository.findById(id).orElseThrow();
            if (user.getNickname().equals("User") ||
                    user.getNickname().equals("Admin") ||
                    user.getNickname().equals("SuperAdmin") ||
                    user.getNickname().equals("Editor") ) {
                throw new IllegalArgumentException("You cannot delete this user");
            } else {
                log.info("delete user with id = {}", id);
                userRepository.delete(user);
            }
        }
    }
    @Transactional
    public void blockUser(long id, boolean locked, User auth) {
        if (!auth.getRoles().contains(Role.ADMIN)) {
            throw new IllegalStateException("You don't have privileges");
        } else {
            log.info("attempt to change the user's lock with id = {}", id);
            User user = userRepository.findById(id).orElseThrow();
            if (user.getNickname().equals("User") ||
                    user.getNickname().equals("Admin") ||
                    user.getNickname().equals("SuperAdmin") ||
                    user.getNickname().equals("Editor") ) {
                throw new IllegalStateException("You cannot block this user");
            } else {
                log.info("change the user's lock with id = {}", id);
                userRepository.blockUser(user.getEmail(), locked);
            }
        }
    }

    @Transactional
    public void changeProfileFields(User user,
                                    String inputType,
                                    String changeTo) {
        log.info(String.format("attempt to change profile data by user %s", user));
        if (user.getEmail().equals("admin@gmail.com") || user.getEmail().equals("user@gmail.com")) {
            throw new IllegalStateException("You cannot change this user's data");
        } else {
            log.info(String.format("change field %s to %s by user %s", inputType, changeTo, user));
            switch (inputType) {
                case "nickname" -> {
                    User byNickname = userRepository.findByNickname(changeTo).orElse(null);
                    if (byNickname == null) {
                        User changeNickname = userRepository.findById(user.getId()).orElseThrow();
                        changeNickname.setNickname(changeTo);
                        userRepository.save(changeNickname);
                    } else if (!byNickname.getId().equals(user.getId())) {
                        throw new FieldAlreadyTakenException(String.format("This %s already taken", inputType));
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
                        throw new FieldAlreadyTakenException(String.format("This %s already taken", inputType));
                    }
                }
            }
        }
    }

    @Transactional
    public void changePassword(User auth,
                               String oldPassword,
                               String newPassword) {
        log.info("attempt to change password by user {}", auth);
        if (auth.getEmail().equals("admin@gmail.com") || auth.getEmail().equals("user@gmail.com")) {
            throw new IllegalStateException("You cannot change this user's data");
        } else {
            log.info("change password by user {}", auth);
            User user = userRepository.findById(auth.getId()).orElseThrow();
            if (!bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
                throw new WrongPasswordException("Wrong old password");
            }
            String invalidPasswordMessage = PasswordValidator.isValidPassword(newPassword);
            if (invalidPasswordMessage != null) {
                throw new WrongPasswordException(invalidPasswordMessage);
            }
            userRepository.changePassword(bCryptPasswordEncoder.encode(newPassword), user.getEmail());
        }
    }

    @Transactional
    public void changeAvatar(User auth,
                             MultipartFile avatar) {
        log.info("change avatar by user {}", auth);
        User user = userRepository.findById(auth.getId()).orElseThrow();
        try {
            user.setAvatar(Base64.getEncoder().encodeToString(avatar.getBytes()));
        } catch (Exception ignored) {
        }
        userRepository.save(user);
    }

    public void deleteProfile(User user) {
        log.info("attempt to delete profile by user {}", user);
        if (user.getEmail().equals("admin@gmail.com") || user.getEmail().equals("user@gmail.com")) {
            throw new IllegalStateException("you cannot delete this user");
        } else {
            log.info("delete user {}", user);
            userRepository.delete(user);
        }
    }
}