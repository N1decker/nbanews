package ru.nidecker.nbanews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.nidecker.nbanews.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Modifying
    @Query(value = "delete from image where id in (select id from image except (select distinct image_id from users))", nativeQuery = true)
    void deleteUnusedImages();
}
