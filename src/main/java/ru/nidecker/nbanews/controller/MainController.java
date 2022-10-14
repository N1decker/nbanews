package ru.nidecker.nbanews.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.nidecker.nbanews.entity.User;
import ru.nidecker.nbanews.repository.NewsRepository;
import ru.nidecker.nbanews.repository.UserRepository;
import ru.nidecker.nbanews.service.NewsService;

import java.util.List;

@Controller
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MainController {

    private final NewsRepository newsRepository;

    private final NewsService newsService;


    @GetMapping
    public String main() {
        return "index";
    }

//
//    @GetMapping("about")
//    public String about() {
//        return "redirect:/";
//    }

    @GetMapping("/news")
    public String news(Model model) {
        model.addAttribute("news", newsRepository.findAllByOrderByIdDesc());
        return "news";
    }

    @PostMapping
    public String save(@RequestParam("title") String title,
                       @RequestParam("image") MultipartFile image,
                       @RequestParam("source") String source,
                       @RequestParam("sourceLogo") MultipartFile sourceLogo) {
        newsService.save(title, image, source, sourceLogo);
        return "redirect:/";
    }
}
