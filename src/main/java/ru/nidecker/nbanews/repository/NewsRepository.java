package ru.nidecker.nbanews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nidecker.nbanews.entity.News;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findAllByOrderByIdDesc();

    List<News> findTopN(int countOfRowsToGet);

    List<News> findAllByOrderByNewsDateDescNewsTimeDesc();

    void deleteById(long id);
}
