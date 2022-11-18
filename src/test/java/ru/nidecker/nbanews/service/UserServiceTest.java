package ru.nidecker.nbanews.service;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.nidecker.nbanews.AbstractTest;
import ru.nidecker.nbanews.entity.Role;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class UserServiceTest extends AbstractTest {

    private static final long NOT_FOUND_USER_ID = 1;

    public static final long ANY_USER_ID = new Random().nextLong();

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ConfirmationTokenService confirmationTokenService;

    @Test
    void signUpWithExistsEmail() {
        String email = RandomString.make(RandomString.DEFAULT_LENGTH);
        User user = new User();
        user.setEmail(email);
        Mockito.when(userRepository
                        .findByEmail(email))
                .thenReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class,
                () -> userService.signUpUser(user));
    }

    @Test
    void deleteUserByNotAdmin() {
        User auth = new User();
        auth.setRoles(Set.of(Role.USER));

        assertThrows(IllegalArgumentException.class,
                () -> userService.deleteUser(ANY_USER_ID, auth));
    }

    @Test
    void deleteNotFoundUser() {
        User auth = new User();
        auth.setRoles(Set.of(Role.ADMIN));

        assertThrows(NoSuchElementException.class,
                () -> userService.deleteUser(NOT_FOUND_USER_ID, auth));
    }

    @Test
    void deleteAdmin() {
        User auth = new User();
        auth.setRoles(Set.of(Role.ADMIN));
        Mockito.when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(new User(
                        "Admin",
                        RandomString.make(),
                        RandomString.make(),
                        Set.of(Role.ADMIN)
                )));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> userService.deleteUser(Mockito.anyLong(), auth));
    }

    @Test
    void deleteUser() {
        User auth = new User();
        auth.setRoles(Set.of(Role.ADMIN));
        User randomUser = new User(
                RandomString.make(),
                RandomString.make(),
                RandomString.make(),
                Set.of(Role.USER)
        );
        Mockito.when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(randomUser));

        userService.deleteUser(Mockito.anyLong(), auth);

        Mockito.verify(userRepository, Mockito.times(1)).delete(randomUser);
    }
}