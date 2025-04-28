package com.mddApi.services;

import com.mddApi.dtos.ArticleRequestDTO;
import com.mddApi.dtos.ArticleResponseDTO;
import com.mddApi.dtos.SubscriptionResponseDTO;
import com.mddApi.exceptions.NotFoundException;
import com.mddApi.models.Article;
import com.mddApi.models.Themes;
import com.mddApi.models.Users;
import com.mddApi.repositories.ArticleRepository;
import com.mddApi.repositories.ThemeRepository;
import com.mddApi.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ThemeRepository themeRepository;

/**
 * @param subscriptions
 * gets all the user's themes subscriptions and returns the articles linked to the themes
 **/
    public List<ArticleResponseDTO> getAllArticles(List<SubscriptionResponseDTO> subscriptions) {
        List<Integer> themeIds = subscriptions
                .stream()
                .map(SubscriptionResponseDTO::getThemeId)
                .collect(Collectors.toList());

        List<Themes> themes = themeRepository.findAllById(themeIds);
        List<Article> articles = articleRepository.findByThemeIn(themes);

        return articles.stream()
                .map(article -> new ArticleResponseDTO(
                        article.getId(),
                        article.getTitle(),
                        article.getContent(),
                        article.getCreatedAt(),
                        article.getAuthor().getUsername(),
                        article.getTheme().getName()
                ))
                .collect(Collectors.toList());
    }

/**
 * @param id
 * @return an article by its id
 **/
    public ArticleResponseDTO getArticleById(Integer id) {
        Optional<Article> articleOpt = articleRepository.findById(id);
        if (articleOpt.isPresent()) {
            Article article = articleOpt.get();

            return new ArticleResponseDTO(
                    article.getId(),
                    article.getTitle(),
                    article.getContent(),
                    article.getCreatedAt(),
                    article.getAuthor().getUsername(),
                    article.getTheme().getName()
            );
        } else {
            throw new NotFoundException("Article not found"); // ou exception custom
        }
    }

    /**
     * @param dto
     * @param authorId
     * @return a new article
     */
    public ArticleResponseDTO createArticle(ArticleRequestDTO dto, Integer authorId) {

        Article article = new Article();
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setCreatedAt(dto.getCreatedAt());

        Users author = new Users();
        author.setId(authorId);
        article.setAuthor(author);

        Themes theme = new Themes();
        theme.setId(dto.getThemeId());
        article.setTheme(theme);

        Article saved = articleRepository.save(article);

        return new ArticleResponseDTO(
                saved.getId(),
                saved.getTitle(),
                saved.getContent(),
                saved.getCreatedAt(),
                saved.getAuthor().getUsername(),
                saved.getTheme().getName()
        );
    }


}
