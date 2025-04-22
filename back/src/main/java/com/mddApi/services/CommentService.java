package com.mddApi.services;

import com.mddApi.dtos.ArticleRequestDTO;
import com.mddApi.dtos.ArticleResponseDTO;
import com.mddApi.dtos.CommentRequestDTO;
import com.mddApi.dtos.CommentResponseDTO;
import com.mddApi.models.Article;
import com.mddApi.models.Comment;
import com.mddApi.models.Themes;
import com.mddApi.models.Users;
import com.mddApi.repositories.CommentRepository;
import com.mddApi.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    JwtService jwtService;

    public List<CommentResponseDTO> getAllComments(Integer articleId){
        List<Comment> comments = commentRepository.findByArticleId(articleId);

        return comments.stream()
                .map(comment -> new CommentResponseDTO(
                        comment.getId(),
                        comment.getContent(),
                        comment.getAuthor().getUsername(),
                        comment.getCreatedAt(),
                        comment.getUpdatedAt(),
                        comment.getArticle().getId()
                ))
                .collect(Collectors.toList());
    }

    public CommentResponseDTO getCommentById(Integer id) {
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if (commentOpt.isPresent()) {
            Comment comment = commentOpt.get();

            return new CommentResponseDTO(
                    comment.getId(),
                    comment.getContent(),
                    comment.getAuthor().getUsername(),
                    comment.getCreatedAt(),
                    comment.getUpdatedAt(),
                    comment.getArticle().getId()
            );
        } else {
            throw new RuntimeException("Article non trouvé"); // ou exception custom
        }
    }

    public CommentResponseDTO createComment(CommentRequestDTO dto, Integer authorId , Integer articleId) {

        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setCreatedAt(dto.getCreatedAt());

        // Associer un utilisateur à partir de son ID
        Users author = new Users();
        author.setId(authorId);
        comment.setAuthor(author);

        // Associer un article à partir de son ID
        Article article = new Article();
        article.setId(articleId);  // <-- Important !
        comment.setArticle(article);  // <-- et pas setArticleId()

        // Sauvegarde en base
        Comment saved = commentRepository.save(comment);

        // Construction de la réponse DTO avec noms de l’auteur et id de l'article
        return new CommentResponseDTO(
                saved.getId(),
                saved.getContent(),
                saved.getAuthor().getUsername(),
                saved.getCreatedAt(),
                saved.getUpdatedAt(),
                saved.getArticleId()
        );
    }
}
