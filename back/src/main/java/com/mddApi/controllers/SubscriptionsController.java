package com.mddApi.controllers;

import com.mddApi.dtos.SubscriptionRequestDTO;
import com.mddApi.dtos.SubscriptionResponseDTO;
import com.mddApi.services.SubscriptionService;
import com.mddApi.utils.ApiResponse;
import com.mddApi.utils.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/themes")
public class SubscriptionsController {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private SubscriptionService subscriptionService;

    private Integer extractUserIdFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token manquant ou invalide");
        }

        String token = authHeader.substring(7); // retire "Bearer "
        return jwtService.extractUserId(token);
    }

    @PostMapping("/subscribe/{themeId}")
    public ResponseEntity<?> subscribe(@PathVariable Integer themeId, HttpServletRequest request) {
        Integer userId = extractUserIdFromToken(request);
        subscriptionService.subscribeToTheme(userId, themeId);
        return ResponseEntity.ok(new ApiResponse("Abonnement réussi !"));
    }

    @PostMapping("/unsubscribe/{themeId}")
    public ResponseEntity<?> unsubscribe(@PathVariable Integer themeId, HttpServletRequest request) {
        Integer userId = extractUserIdFromToken(request);
        subscriptionService.unsubscribeFromTheme(userId, themeId);
        return ResponseEntity.ok(new ApiResponse("Désabonnement réussi !"));
    }


    @GetMapping("/subscriptions")
    public ResponseEntity<List<SubscriptionResponseDTO>> getUserSubscriptions(HttpServletRequest request) {
        Integer userId = extractUserIdFromToken(request);
        List<SubscriptionResponseDTO> subscriptions = subscriptionService.getUserSubscriptions(userId);
        return ResponseEntity.ok(subscriptions);
    }

}
