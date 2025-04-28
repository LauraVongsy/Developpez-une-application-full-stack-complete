package com.mddApi.controllers;

import com.mddApi.dtos.CommentRequestDTO;
import com.mddApi.dtos.CommentResponseDTO;
import com.mddApi.services.CommentService;
import com.mddApi.utils.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller handling all the Comment related endpoints
 */
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private JwtService jwtService;

    private Integer extractUserIdFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid token");
        }

        String token = authHeader.substring(7); // retire "Bearer "
        return jwtService.extractUserId(token);
    }

    /**
     *
     * @param articleId
     * @return all the comments of an article
     */
    @GetMapping("/articles/{id}/comments")
    public List<CommentResponseDTO> getAllComments(@PathVariable("id") Integer articleId){
        return commentService.getAllComments(articleId);
    }

    /**
     *
     * @param articleId
     * @param dto
     * @param request
     * @return creates the comment left by the user
     */
    @PostMapping("/articles/{id}/comments")
    public CommentResponseDTO createComment(@PathVariable("id") Integer articleId,
                                            @RequestBody @Valid CommentRequestDTO dto,
                                            HttpServletRequest request) {
        Integer userId = extractUserIdFromToken(request);
        return commentService.createComment(dto, userId, articleId);
    }

}
