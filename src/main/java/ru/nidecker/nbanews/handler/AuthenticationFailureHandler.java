package ru.nidecker.nbanews.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.repository.UserRepository;
import ru.nidecker.nbanews.util.validation.EmailValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final String WRONG_PASSWORD_MSG = "You entered the wrong password";

    public static final String BLOCKED_ACCOUNT_MSG = "Your account is blocked, you can find out the reason for blocking by writing to this email address: nb4news@gmail.com and maybe we will unblock your account";

    public static final String USER_NOT_FOUND_MSG = "There is no such user";

    public static final String NOT_VALID_EMAIL = "It doesn't look like an email";
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        String email = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userRepository.findByEmail(email).orElse(null);
        String message = "";
        boolean isValidEmail = EmailValidator.validate(email);
        if (!isValidEmail) {
            message = NOT_VALID_EMAIL;
        } else if (user != null && user.isLocked()) {
            message = BLOCKED_ACCOUNT_MSG;
        } else if (user != null && !bCryptPasswordEncoder.matches(password, user.getPassword())) {
            message = WRONG_PASSWORD_MSG;
        } else if (user == null) {
            message = USER_NOT_FOUND_MSG;
        }
        response.sendError(401, message);
    }
}