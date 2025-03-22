package com.mddApi.controllers;

import com.mddApi.dtos.UserRequestDTO;
import com.mddApi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController

public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("auth/register")

    public ResponseEntity<Map<String, String>> register(@RequestBody UserRequestDTO userRequestDTO) {
        String token = userService.register(userRequestDTO);
        if (token != null) {
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(400).body(Map.of("error", "register failed"));
        }
    }
}
