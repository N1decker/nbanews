package ru.nidecker.nbanews.service.newsParsing;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nidecker.nbanews.entity.News;
import ru.nidecker.nbanews.entity.NewsSource;
import ru.nidecker.nbanews.repository.NewsRepository;
import ru.nidecker.nbanews.service.NewsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsParser {

    private final NewsRepository newsRepository;
    private final NewsService newsService;

    @Scheduled(fixedRate = 30000, timeUnit = TimeUnit.SECONDS, initialDelay = 30)
    @SneakyThrows
    public void parse() {
        parseEspnCom();
    }

    @Transactional
    protected void parseEspnCom() throws IOException {
        NewsSource newsSource = newsService.chooseNewsSource("espn");
        Document document = Jsoup.connect("https://www.espn.com/nba/").get();

        Elements content = document.getElementsByClass("has-image");

        List<News> news = new ArrayList<>();

        for (Element element : content) {

            String originalSource = element.getElementsByTag("a").size() == 0 ? "" :
                    "https://www.espn.com" + element.getElementsByTag("a").get(0).attr("href");

            if (originalSource.equals("")) continue;

            String title = element.getElementsByTag("h1").size() == 0 ? "" :
                    element.getElementsByTag("h1").get(0).text();

            String subhead = element.getElementsByTag("p").size() == 0 ? "" :
                    element.getElementsByTag("p").get(0).text();

            String contentAuthor = element.getElementsByClass("contentMeta__author").size() == 0 ?
                    "" : element.getElementsByClass("contentMeta__author").get(0).text();

            String imageURL = element.getElementsByTag("picture").size() == 0 ? "" :
                    element.getElementsByTag("picture").get(0)
                            .getElementsByTag("img").get(0).attr("data-default-src");

            news.add(mapToNewsEntity(title, subhead, originalSource, contentAuthor, imageURL, newsSource));
        }

        deleteSimilarNews(news, newsRepository.findTopN(10));

        if (news.size() > 0) {
            newsService.saveAll(news);
        }
    }

    private void deleteSimilarNews(List<News> newNews, List<News> lastNews) {
        List<News> existedNews = new ArrayList<>();

        for (News old : lastNews) {
            for (News newN : newNews) {
                if (newN.getTitle().equals(old.getTitle())) existedNews.add(newN);
            }
        }

        newNews.removeAll(existedNews);
    }

    private News mapToNewsEntity(String title, String subhead, String originalSource, String contentAuthor, String imageURL, NewsSource siteName) {
        switch (siteName.getName()) {
            case "espn" -> {
                return newsService.prepare(title, subhead, originalSource, imageURL, siteName, contentAuthor);
            }
            case "andscape" -> {
            }
        }
        return null;
    }
}