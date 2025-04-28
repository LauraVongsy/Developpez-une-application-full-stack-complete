package com.mddApi.controllers;

import com.mddApi.dtos.ArticleRequestDTO;
import com.mddApi.dtos.ArticleResponseDTO;
import com.mddApi.dtos.SubscriptionResponseDTO;
import com.mddApi.services.ArticleService;
import com.mddApi.services.SubscriptionService;
import com.mddApi.utils.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling articles related endpoints
 */
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private JwtService jwtService;

    /**
     *
     * @param request
     * gets the user id from the token
     */
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
     * @param userDetails
     * @param request
     * @return all the articles related to the themes subscribed by the user
     */
     @GetMapping("/articles")
     public List<ArticleResponseDTO> getAllArticles(@AuthenticationPrincipal UserDetails userDetails ,  HttpServletRequest request) {
         Integer userId = extractUserIdFromToken(request);
        List<SubscriptionResponseDTO> userThemes = subscriptionService.getUserSubscriptions(userId);
        return articleService.getAllArticles(userThemes);
    }

    /**
     *
     * @param id
     * @return an article by its id
     */
   @GetMapping("/articles/{id}")
     public ArticleResponseDTO getArticleById(@PathVariable Integer id) {
        return articleService.getArticleById(id);
    }

    /**
     *
     * @param dto
     * @param request
     * @return a new article created by the user
     */
   @PostMapping("/articles")
    public ArticleResponseDTO createArticle(@Valid @RequestBody ArticleRequestDTO dto , HttpServletRequest request) {
        Integer userId = extractUserIdFromToken(request);
        return articleService.createArticle(dto, userId );
    }

}
