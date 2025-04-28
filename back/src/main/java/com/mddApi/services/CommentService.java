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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleRepository articleRepository;

    /**
     *
     * @param articleId
     * @return all the comments for an article
     */
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

    /**
     *
     * @param id
     * @return a comment by its id
     */
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
            throw new NotFoundException("Article not found");
        }
    }

    /**
     *
     * @param dto
     * @param authorId
     * @param articleId
     * @return a new comment
     */
    public CommentResponseDTO createComment(CommentRequestDTO dto, Integer authorId, Integer articleId) {
        if (dto.getContent() == null || dto.getContent().isBlank()) {
            throw new BadRequestException("Comment must contain text");
        }

        Users author = userService.findById(authorId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new NotFoundException("Article not found"));

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
