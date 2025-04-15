package com.mddApi.controllers;

import com.mddApi.dtos.ArticleRequestDTO;
import com.mddApi.dtos.ArticleResponseDTO;
import com.mddApi.services.ArticleService;
import com.mddApi.utils.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class ArticleController {
    @Autowired
    ArticleService articleService;
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
     @GetMapping("/articles")
     public List<ArticleResponseDTO> getAllArticles() {
        return articleService.getAllArticles();
    }
    // Endpoint to get an article by ID
   @GetMapping("/articles/{id}")
     public ArticleResponseDTO getArticleById(@PathVariable Integer id) {
        return articleService.getArticleById(id);
    }
    // Endpoint to create a new article
   @PostMapping("/articles")
    public ArticleResponseDTO createArticle(@RequestBody ArticleRequestDTO dto , HttpServletRequest request) {
        Integer userId = extractUserIdFromToken(request);
        return articleService.createArticle(dto, userId );
    }
    // Endpoint to update an article
    @PutMapping("/articles/{id}")
   public ArticleResponseDTO updateArticle(@PathVariable Integer id, @RequestBody ArticleRequestDTO dto) {
      return articleService.updateArticle(id, dto);
}
    // Endpoint to delete an article
    @DeleteMapping("/articles/{id}")
   public void deleteArticle(@PathVariable Integer id) {
       articleService.deleteArticle(id);
    }


}
