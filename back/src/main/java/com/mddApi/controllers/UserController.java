package com.mddApi.controllers;

import com.mddApi.dtos.UserRequestDTO;
import com.mddApi.dtos.UserUpdateDTO;
import com.mddApi.services.UserService;
import com.mddApi.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller for handling user-related endpoints.
 * Provides functionalities like user registration, login, update, and user data retrieval.
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    /**
     * Registers a new user and returns a JWT token.
     *
     * @param userRequestDTO the user registration details
     * @return ResponseEntity containing the JWT token if registration is successful
     * @throws com.mddApi.exceptions.BadRequestException if the email is already in use
     */
    @PostMapping("auth/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserRequestDTO userRequestDTO) {
        String token = userService.register(userRequestDTO);
        if (token != null) {
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(400).body(Map.of("error", "register failed"));
        }
    }

    /**
     * Logs in an existing user and returns a JWT token.
     *
     * @param userDTO the user's login details (email and password)
     * @return ResponseEntity containing the JWT token if authentication is successful
     * @throws com.mddApi.exceptions.NotFoundException if the user is not found
     * @throws com.mddApi.exceptions.BadRequestException if the password is incorrect
     */
    @PostMapping("auth/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserRequestDTO userDTO) {
        String token = userService.login(userDTO);
        if (token != null) {
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Incorrect login or password"));
        }
    }

    /**
     * Updates the user's information (e.g., username, password).
     *
     * @param userUpdateDTO the updated user details
     * @param userUpdateDTO the ID of the user to update
     * @return ResponseEntity containing the updated user information or a new JWT token
     * @throws com.mddApi.exceptions.NotFoundException if the user to be updated is not found
     */
    @PutMapping("user/update")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDTO userUpdateDTO, @RequestHeader("Authorization") String token
    ) {
        String tokenValue = token.substring(7);

        Integer userId = jwtService.extractUserId(tokenValue);

        userService.updateUser(userUpdateDTO, userId);

            String newToken = jwtService.generateToken(userUpdateDTO.getEmail(), userId);

            return ResponseEntity.ok().body(Map.of(
                    "message", "Profile updated with success",
                    "token", newToken
            ));

    }

}
