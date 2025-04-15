package com.mddApi.repositories;

import com.mddApi.models.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> findAll();
    List<Article> findByThemeId(int themeId);
    List<Article> findByAuthor_Id(int authorId);
    boolean existsByAuthor_IdAndTheme_Id(int authorId, int themeId);
    void deleteByAuthor_IdAndTheme_Id(int authorId, int themeId);
}
