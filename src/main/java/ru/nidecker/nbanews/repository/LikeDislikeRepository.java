package ru.nidecker.nbanews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nidecker.nbanews.entity.LikeDislike;

import java.util.List;
import java.util.Optional;

public interface LikeDislikeRepository extends JpaRepository<LikeDislike, Long> {

    List<LikeDislike> findAllByUserId(long userId);

    Optional<LikeDislike> getByUserIdAndNewsId(long userId, long newsId);
}
