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
import ru.nidecker.nbanews.entity.News;
import ru.nidecker.nbanews.entity.NewsSource;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsParser {

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.MINUTES, initialDelay = 1)
    @SneakyThrows
    public static void parse() {
        parseEspnCom();
    }

    private static void parseEspnCom() throws IOException {
        Document document = Jsoup.connect("https://www.espn.com/nba/").get();

        Elements content = document.getElementsByClass("has-image");

        for (Element element : content) {
            String title = element.getElementsByTag("h1").size() == 0 ? null :
                    element.getElementsByTag("h1").get(0).text();

            String subhead = element.getElementsByTag("p").size() == 0 ? null :
                    element.getElementsByTag("p").get(0).text();

            String originalSource = element.getElementsByTag("a").size() == 0 ? null :
                    "https://www.espn.com" + element.getElementsByTag("a").get(0).attr("href");

            String contentAuthor = element.getElementsByClass("contentMeta__author").size() == 0 ?
                    null : element.getElementsByClass("contentMeta__author").get(0).text();

            String imgSrc = element.getElementsByTag("picture").size() == 0 ? null :
                    element.getElementsByTag("picture").get(0)
                            .getElementsByTag("img").get(0).attr("data-default-src");

            log.info("\n ------------------------------ \n {} \n {} \n {} \n {} \n {} \n ------------------------------ \n",
                    title, subhead, contentAuthor, originalSource, imgSrc);

            mapToNewsEntity(title, subhead, originalSource, contentAuthor, imgSrc, NewsSource.SourceName.ESPN);
        }
    }

    private static void mapToNewsEntity(String title, String subhead, String originalSource, String contentAuthor, String imgSrc, NewsSource.SourceName siteName) {
        News news = new News();

        switch (siteName) {
            case ESPN -> {
//                news.setSource
            }
        }
    }

}
