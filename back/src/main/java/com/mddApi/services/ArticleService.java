package com.mddApi.services;

import com.mddApi.dtos.ArticleRequestDTO;
import com.mddApi.dtos.ArticleResponseDTO;
import com.mddApi.dtos.SubscriptionResponseDTO;
import com.mddApi.models.Article;
import com.mddApi.models.Subscriptions;
import com.mddApi.models.Themes;
import com.mddApi.models.Users;
import com.mddApi.repositories.ArticleRepository;
import com.mddApi.repositories.ThemeRepository;
import com.mddApi.utils.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ThemeRepository themeRepository;
    @Autowired
    JwtService jwtService;

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
            throw new RuntimeException("Article non trouvé"); // ou exception custom
        }
    }

    public ArticleResponseDTO createArticle(ArticleRequestDTO dto, Integer authorId) {

        Article article = new Article();
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setCreatedAt(dto.getCreatedAt());

        // Associer un utilisateur à partir de son ID
        Users author = new Users();
        author.setId(authorId);
        article.setAuthor(author);

        // Associer un thème à partir de son ID
        Themes theme = new Themes();
        theme.setId(dto.getThemeId());
        article.setTheme(theme);

        // Sauvegarde en base
        Article saved = articleRepository.save(article);

        // Construction de la réponse DTO avec noms de l’auteur et du thème
        return new ArticleResponseDTO(
                saved.getId(),
                saved.getTitle(),
                saved.getContent(),
                saved.getCreatedAt(),
                saved.getAuthor().getUsername(), // Assure-toi que ce getter existe bien
                saved.getTheme().getName()       // Idem ici
        );
    }

    public ArticleResponseDTO updateArticle(Integer id ,ArticleRequestDTO dto){
        Article article = new Article();
        article.setId(id);
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setCreatedAt(dto.getCreatedAt());

        // Associer un utilisateur à partir de son ID
        Users author = new Users();
        author.setId(dto.getAuthorId());
        article.setAuthor(author);

        // Associer un thème à partir de son ID
        Themes theme = new Themes();
        theme.setId(dto.getThemeId());
        article.setTheme(theme);

        // Sauvegarde en base
        Article saved = articleRepository.save(article);

        // Construction de la réponse DTO avec noms de l’auteur et du thème
        return new ArticleResponseDTO(
                saved.getId(),
                saved.getTitle(),
                saved.getContent(),
                saved.getCreatedAt(),
                saved.getAuthor().getUsername(), // Assure-toi que ce getter existe bien
                saved.getTheme().getName()       // Idem ici
        );
    }

    public void deleteArticle(Integer id) {
        articleRepository.deleteById(id);
    }
}
