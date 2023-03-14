package ru.nidecker.nbanews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.nidecker.nbanews.entity.News;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findAllByOrderByIdDesc();

    @Query(value = "select * from News order by news_date desc, news_time desc LIMIT :limit", nativeQuery = true)
    List<News> findTopN(int limit);

    void deleteById(long id);

    @Modifying
    @Query("update News set title = :title, subtitle = :subtitle, contentAuthor = :contentAuthor, imageUrl = :imageUrl where id = :id")
    void updateById(long id, String title, String subtitle, String contentAuthor, String imageUrl);
}
