package ru.nidecker.nbanews.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.nidecker.nbanews.AbstractTest;
import ru.nidecker.nbanews.dto.RegistrationRequest;
import ru.nidecker.nbanews.entity.ConfirmationToken;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.exception.WrongPasswordException;
import ru.nidecker.nbanews.repository.ConfirmationTokenRepository;
import ru.nidecker.nbanews.repository.UserRepository;
import ru.nidecker.nbanews.util.email.EmailSender;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegistrationServiceTest extends AbstractTest {

    @MockBean
    private UserService userService;

    @MockBean
    private EmailSender emailSender;

    @MockBean
    private ConfirmationTokenService confirmationTokenService;

    @MockBean
    private ConfirmationTokenRepository confirmationTokenRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private RegistrationService registrationService;

    @Test
    void register() {
        RegistrationRequest request =
                new RegistrationRequest(
                        "newUser",
                        "validemail@test.com",
                        "Val1d!Pass{"
                );
        Assertions.assertTrue(registrationService.register(request));
    }

    @Test
    void registerWithInvalidEmail() {
        RegistrationRequest request =
                new RegistrationRequest(
                        "",
                        "invalid_email",
                        ""
                );

        assertThrows(IllegalArgumentException.class,
                () -> registrationService.register(request));
    }

    @Test
    void registerWithInvalidPassword() {
        RegistrationRequest request =
                new RegistrationRequest(
                        "",
                        "test@gmail.com",
                        "invalid_pass"
                );

        Assertions.assertThrows(WrongPasswordException.class,
                () -> registrationService.register(request));
    }

    @Test
    void confirmTokenWithEmptyToken() {
        String token = UUID.randomUUID().toString();

        assertThrows(IllegalArgumentException.class,
                () -> registrationService.confirmToken(token));

        Mockito.verify(confirmationTokenService, Mockito.times(1)).getToken(token);
    }

    @Test
    void confirmTokenMethodWithConfirmedToken() {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setConfirmedAt(LocalDateTime.now().minusMinutes(1));

        Mockito.doReturn(Optional.of(confirmationToken))
                .when(confirmationTokenService)
                .getToken(Mockito.anyString());

        assertThrows(IllegalArgumentException.class,
                () -> registrationService.confirmToken(token));
    }

    @Test
    void confirmTokenMethodWithExpiredToken() {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setExpiresAt(LocalDateTime.now().minusMinutes(1));

        Mockito.doReturn(Optional.of(confirmationToken))
                .when(confirmationTokenService)
                .getToken(Mockito.anyString());

        assertThrows(IllegalArgumentException.class,
                () -> registrationService.confirmToken(token));
    }

    @Test
    public void confirmToken() {
        User user = new User();
        user.setEmail("email@gmail.com");

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setUser(user);
        confirmationToken.setToken(token);
        confirmationToken.setExpiresAt(LocalDateTime.now().plusMinutes(10));

        Mockito.doReturn(Optional.of(confirmationToken))
                .when(confirmationTokenService)
                .getToken(Mockito.anyString());

        Mockito.doReturn(1)
                .when(confirmationTokenRepository)
                .updateConfirmedAt(Mockito.anyString(), Mockito.any(LocalDateTime.class));

        Mockito.doNothing()
                .when(userService)
                .enableUser(Mockito.anyString());

        String resultOfConfirmToken = registrationService.confirmToken(token);

        Mockito.verify(confirmationTokenService, Mockito.times(1)).setConfirmedAt(token);
        Mockito.verify(userService, Mockito.times(1)).enableUser(confirmationToken.getUser().getEmail());

        Assertions.assertSame("confirmed", resultOfConfirmToken);
    }
}