package com.mddApi.repositories;

import com.mddApi.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAll();
    List<Comment> findByArticleId(int articleId);
    List<Comment> findByAuthor_Id(int authorId);
    boolean existsByAuthor_IdAndArticle_Id(int authorId, int articleId);
    void deleteByAuthor_IdAndArticle_Id(int authorId, int articleId);
}
