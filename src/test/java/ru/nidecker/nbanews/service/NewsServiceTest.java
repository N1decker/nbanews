//package ru.nidecker.nbanews.service;
//
//import lombok.SneakyThrows;
//import org.assertj.core.internal.bytebuddy.utility.RandomString;
//import org.junit.Ignore;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//import ru.nidecker.nbanews.AbstractTest;
//import ru.nidecker.nbanews.entity.News;
//import ru.nidecker.nbanews.entity.Role;
//import ru.nidecker.nbanews.entity.User;
//import ru.nidecker.nbanews.repository.NewsRepository;
//
//import java.util.Base64;
//import java.util.List;
//import java.util.Random;
//import java.util.Set;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//
//class NewsServiceTest extends AbstractTest {
//
//    public static final long ANY_NEWS_ID = new Random().nextLong();
//
//    @Autowired
//    private NewsService newsService;
//
//    @MockBean
//    private NewsRepository newsRepository;
//
//    @SneakyThrows
//    @Test
//    public void save() {
//        String title = RandomString.make();
//        MultipartFile image =
//                new MockMultipartFile(
//                        RandomString.make(),
//                        RandomString.make().getBytes()
//                );
//        String source = generateRandomValidUrl();
//        MultipartFile sourceLogo =
//                new MockMultipartFile(
//                        RandomString.make(),
//                        RandomString.make().getBytes()
//                );
//
//        User auth = new User();
//        auth.setRoles(Set.of(Role.EDITOR));
//        auth.setNickname(RandomString.make(RandomString.DEFAULT_LENGTH));
//
//        News expected =
//                new News(
//                        title,
//                        Base64.getEncoder().encodeToString(image.getBytes()),
//                        source,
//                        Base64.getEncoder().encodeToString(sourceLogo.getBytes()),
//                        auth.getNickname());
//
//        Mockito.when(newsRepository.save(any(News.class)))
//                .thenReturn(expected);
//
//        News intermediate =
//                newsService.save(title,
//                        image,
//                        source,
//                        sourceLogo,
//                        auth);
//
//        News actual = new News(
//                intermediate.getTitle(),
//                intermediate.getImageURL(),
//                intermediate.getSource(),
//                intermediate.getSourceLogo(),
//                intermediate.getEditor()
//        );
//
//        assertThat(actual).isEqualToComparingFieldByField(expected);
//    }
//
//    @Test
//    void saveByNotEditor() {
//        User user = new User();
//        user.setRoles(Set.of(Role.USER));
//
//        assertThrows(
//                IllegalArgumentException.class,
//                () ->
//                        newsService.save(
//                                RandomString.make(RandomString.DEFAULT_LENGTH),
//                                new MockMultipartFile(
//                                        RandomString.make(),
//                                        RandomString.make().getBytes()
//                                ),
//                                RandomString.make(RandomString.DEFAULT_LENGTH),
//                                new MockMultipartFile(
//                                        RandomString.make(),
//                                        RandomString.make().getBytes()
//                                ),
//                                user)
//        );
//    }
//
//    @Test
//    void saveWithInvalidSourceUrl() {
//        User user = new User();
//        user.setRoles(Set.of(Role.EDITOR));
//
//        assertThrows(
//                IllegalArgumentException.class,
//                () ->
//                        newsService.save(
//                                RandomString.make(RandomString.DEFAULT_LENGTH),
//                                new MockMultipartFile(
//                                        RandomString.make(),
//                                        RandomString.make().getBytes()
//                                ),
//                                generateRandomInvalidUrl(),
//                                new MockMultipartFile(
//                                        RandomString.make(),
//                                        RandomString.make().getBytes()
//                                ),
//                                user)
//        );
//    }
//
//    @Test
//    void deleteByNotEditor() {
//        User user = new User();
//        user.setRoles(Set.of(Role.USER));
//
//        assertThrows(
//                IllegalArgumentException.class,
//                () ->
//                        newsService.delete(ANY_NEWS_ID, user)
//        );
//    }
//
//    @Test
//    void delete() {
//        User user = new User();
//        user.setRoles(Set.of(Role.EDITOR));
//
//        newsService.delete(ANY_NEWS_ID, user);
//
//        Mockito.verify(newsRepository, Mockito.times(1)).deleteById(ANY_NEWS_ID);
//    }
//
//    private String generateRandomValidUrl() {
//        StringBuilder builder = new StringBuilder();
//        if (new Random().nextBoolean()) builder.append("https://");
//        builder
//                .append("www.")
//                .append(RandomString.make(10))
//                .append(".")
//                .append(RandomString.make(3))
//                .append("/")
//                .append(RandomString.make(RandomString.DEFAULT_LENGTH));
//
//        return builder.toString();
//    }
//
//    private String generateRandomInvalidUrl() {
//        StringBuilder builder = new StringBuilder();
//        List<String> randomElementsOfUrl =
//                List.of("/", ".", ":", "?", "-",
//                        RandomString.make(RandomString.DEFAULT_LENGTH),
//                        RandomString.make(RandomString.DEFAULT_LENGTH),
//                        RandomString.make(RandomString.DEFAULT_LENGTH),
//                        RandomString.make(RandomString.DEFAULT_LENGTH),
//                        RandomString.make(RandomString.DEFAULT_LENGTH));
//
//        builder.append(RandomString.make(7));
//        for (int i = 0; i < 5; i++) {
//            int randomNum = new Random().nextInt(9);
//            builder.append(randomElementsOfUrl.get(randomNum));
//        }
//        return builder.toString();
//    }
//}