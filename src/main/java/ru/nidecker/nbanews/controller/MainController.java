package ru.nidecker.nbanews.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping
    public String main() {
        return "index";
    }

    @GetMapping("users")
    public String users() {
        return "redirect:/";
    }

    @GetMapping("about")
    public String about() {
        return "redirect:/";
    }

    @GetMapping("news")
    public String news() {
        return "redirect:/";
    }
}
