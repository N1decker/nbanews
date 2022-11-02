package ru.nidecker.nbanews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.nidecker.nbanews.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

//    @Query("select c from Comment c where c.news.id= :id")
    List<Comment> findAllByNewsId(long id);

    @Modifying
    @Query("delete from Comment c where c.user.id = :userId")
    void deleteAllByUserId(long userId);
}
