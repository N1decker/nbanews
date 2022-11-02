package ru.nidecker.nbanews.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MainController {

    @GetMapping
    public String main() {
        return "index";
    }
}