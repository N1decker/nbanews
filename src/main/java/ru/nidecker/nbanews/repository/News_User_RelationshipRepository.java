package ru.nidecker.nbanews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nidecker.nbanews.entity.News_User_Relationship;

import java.util.List;

public interface News_User_RelationshipRepository extends JpaRepository<News_User_Relationship, Long> {

    List<News_User_Relationship> findAllByNewsId(long id);
}
