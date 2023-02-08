package ru.nidecker.nbanews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nidecker.nbanews.entity.NewsSource;

public interface NewsSourceLogoRepository extends JpaRepository<NewsSource, Long> {

    NewsSource findNewsSourceByNameIgnoreCase(String name);
}
