package com.mddApi.services;


import com.mddApi.dtos.CommentRequestDTO;
import com.mddApi.dtos.CommentResponseDTO;
import com.mddApi.exceptions.BadRequestException;
import com.mddApi.exceptions.NotFoundException;
import com.mddApi.models.Article;
import com.mddApi.models.Comment;
import com.mddApi.models.Users;
import com.mddApi.repositories.ArticleRepository;
import com.mddApi.repositories.CommentRepository;
import com.mddApi.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    UserService userService;
    @Autowired
    ArticleRepository articleRepository;


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
            throw new NotFoundException("Article non trouvé"); // ou exception custom
        }
    }

    public CommentResponseDTO createComment(CommentRequestDTO dto, Integer authorId, Integer articleId) {
        if (dto.getContent() == null || dto.getContent().isBlank()) {
            throw new BadRequestException("Le contenu du commentaire ne peut pas être vide");
        }

        Users author = userService.findById(authorId)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé"));

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NotFoundException("Article non trouvé"));

        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setAuthor(author);
        comment.setArticle(article);
        comment.setCreatedAt(LocalDateTime.now());

        Comment saved = commentRepository.save(comment);

        return new CommentResponseDTO(
                saved.getId(),
                saved.getContent(),
                author.getUsername(),
                saved.getCreatedAt(),
                saved.getUpdatedAt(),
                article.getId()
        );
    }

}
