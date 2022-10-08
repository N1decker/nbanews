package ru.nidecker.nbanews.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.nidecker.nbanews.repository.NewsRepository;
import ru.nidecker.nbanews.service.NewsService;

@Controller
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class MainController {

    private final NewsRepository newsRepository;

    private final NewsService newsService;

    public MainController(NewsRepository newsRepository, NewsService newsService) {
        this.newsRepository = newsRepository;
        this.newsService = newsService;
    }

//    @GetMapping
//    public String main() {
//        return "index";
//    }

    @GetMapping("users")
    public String users() {
        return "redirect:/";
    }

    @GetMapping("about")
    public String about() {
        return "redirect:/";
    }

    @GetMapping
    public String news(Model model) {
        model.addAttribute("news", newsRepository.findAllByOrderByIdDesc());
        return "index";
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
