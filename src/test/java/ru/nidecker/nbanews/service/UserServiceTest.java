package ru.nidecker.nbanews.service;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.nidecker.nbanews.AbstractTest;
import ru.nidecker.nbanews.entity.Role;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest extends AbstractTest {

    private static final long NOT_FOUND_USER_ID = 1;

    public static final long ANY_USER_ID = new Random().nextLong();

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ConfirmationTokenService confirmationTokenService;

    @Test(expected = IllegalArgumentException.class)
    public void signUpWithExistsEmail() {
        User user = new User();
        user.setEmail(Mockito.anyString());
        Mockito.when(userRepository
                        .findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));

        userService.signUpUser(user);
    }

    @Test(expected = IllegalStateException.class)
    public void deleteUserByNotAdmin() {
        User auth = new User();
        auth.setRoles(Set.of(Role.USER));
        userService.deleteUser(ANY_USER_ID, auth);
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteNotFoundUser() {
        User auth = new User();
        auth.setRoles(Set.of(Role.ADMIN));
        userService.deleteUser(NOT_FOUND_USER_ID, auth);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteAdmin() {
        User auth = new User();
        auth.setRoles(Set.of(Role.ADMIN));
        Mockito.when(userRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(new User(
                        RandomString.make(),
                        RandomString.make(),
                        RandomString.make(),
                        Set.of(Role.ADMIN)
                )));

        userService.deleteUser(Mockito.anyLong(), auth);
    }

    @Test
    public void deleteUser() {
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