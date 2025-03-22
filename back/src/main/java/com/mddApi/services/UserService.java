package com.mddApi.services;

import com.mddApi.dtos.UserRequestDTO;
import com.mddApi.models.Users;
import com.mddApi.repositories.UserRepository;
import com.mddApi.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String register(UserRequestDTO userRequestDTO) {

        if (userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            return "This email is already in use";
        }

        Users user = new Users();
        user.setEmail(userRequestDTO.getEmail());
        user.setName(userRequestDTO.getName());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        userRepository.save(user);
        return jwtService.generateToken(userRequestDTO.getEmail(), user.getId());
    }
}
