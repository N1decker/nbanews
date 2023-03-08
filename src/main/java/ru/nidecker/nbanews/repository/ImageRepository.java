package ru.nidecker.nbanews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nidecker.nbanews.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

//    @Query(value = "delete from image where id not in ()", nativeQuery = true)
//    @Modifying
//    void deleteUnusedImages();
}
