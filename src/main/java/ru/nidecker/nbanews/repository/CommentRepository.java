package ru.nidecker.nbanews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nidecker.nbanews.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByNewsId(long id);
}
