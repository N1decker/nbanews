package ru.nidecker.nbanews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.nidecker.nbanews.entity.News;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findAllByOrderByIdDesc();

    @Query(value = "select * from News order by news_date desc, news_time desc LIMIT :limit", nativeQuery = true)
    List<News> findTopN(int limit);

    List<News> findAllByOrderByNewsDateDescNewsTimeDesc();

    void deleteById(long id);
}
