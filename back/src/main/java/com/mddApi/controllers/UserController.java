package com.mddApi.controllers;

import com.mddApi.dtos.UserRequestDTO;
import com.mddApi.dtos.UserUpdateDTO;
import com.mddApi.services.UserService;
import com.mddApi.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController

public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("auth/register")

    public ResponseEntity<Map<String, String>> register(@RequestBody UserRequestDTO userRequestDTO) {
        String token = userService.register(userRequestDTO);
        if (token != null) {
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(400).body(Map.of("error", "register failed"));
        }
    }

    @PostMapping("auth/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserRequestDTO userDTO) {
        String token = userService.login(userDTO);
        if (token != null) {
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Identifiants incorrects"));
        }
    }

    @PutMapping("user/update")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDTO userUpdateDTO, @RequestHeader("Authorization") String token
    ) {
        // Supprimer le préfixe "Bearer " du token
        String tokenValue = token.substring(7);

        // Extraire l'ID utilisateur et le nom d'utilisateur du token
        Integer userId = jwtService.extractUserId(tokenValue);


        // Appeler le service pour mettre à jour l'utilisateur avec l'ID et le nom d'utilisateur extraits
        userService.updateUser(userUpdateDTO, userId);

            String newToken = jwtService.generateToken(userUpdateDTO.getEmail(), userId);

            // Envoi du nouveau token en réponse
            return ResponseEntity.ok().body(Map.of(
                    "message", "Profil mis à jour avec succès",
                    "token", newToken
            ));

    }

}
