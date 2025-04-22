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

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    JwtService jwtService;

    private Integer extractUserIdFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token manquant ou invalide");
        }

        String token = authHeader.substring(7); // retire "Bearer "
        return jwtService.extractUserId(token);
    }

    @GetMapping("/articles/{id}/comments")
    public List<CommentResponseDTO> getAllComments(@PathVariable("id") Integer articleId){
        return commentService.getAllComments(articleId);
    }
    @PostMapping("/articles/{id}/comments")
    public CommentResponseDTO createComment(@PathVariable("id") Integer articleId,
                                            @RequestBody @Valid CommentRequestDTO dto,
                                            HttpServletRequest request) {
        Integer userId = extractUserIdFromToken(request);
        return commentService.createComment(dto, userId, articleId);
    }

}
