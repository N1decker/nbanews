package ru.nidecker.nbanews.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        StringBuilder builder = new StringBuilder();
        String email = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null && !bCryptPasswordEncoder.matches(password, user.getPassword())) {
//        response.sendError(401, "Authentication Failed: " + exception.getMessage());
            response.sendError(401, "You entered the wrong password");
        } else if (user == null) {
            response.sendError(401, "There is no such user");
        }
    }
}