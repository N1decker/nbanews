package ru.nidecker.nbanews.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nidecker.nbanews.dto.RegistrationRequest;
import ru.nidecker.nbanews.service.RegistrationService;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public void register(RegistrationRequest request) {
         registrationService.register(request);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
