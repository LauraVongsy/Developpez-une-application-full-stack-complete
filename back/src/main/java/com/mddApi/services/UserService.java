package com.mddApi.services;

import com.mddApi.dtos.UserRequestDTO;
import com.mddApi.dtos.UserResponseDTO;
import com.mddApi.dtos.UserUpdateDTO;
import com.mddApi.exceptions.BadRequestException;
import com.mddApi.exceptions.NotFoundException;
import com.mddApi.mappers.UsersMapper;
import com.mddApi.models.Users;
import com.mddApi.repositories.UserRepository;
import com.mddApi.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsersMapper usersMapper;

    /**
     * Find a user by their unique ID.
     *
     * @param userId the ID of the user
     * @return an Optional containing the found user, or empty if no user was found
     */
    public Optional<Users> findById(Integer userId){
        return userRepository.findById(userId);
    }

    /**
     * Registers a new user in the system.
     * Throws an exception if the email is already in use.
     *
     * @param userRequestDTO the user registration information
     * @return a JWT token for the newly registered user
     * @throws BadRequestException if the email is already registered
     */
    public String register(UserRequestDTO userRequestDTO) {
        if (userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            throw new BadRequestException("This email is already in use");
        }

        Users user = new Users();
        user.setEmail(userRequestDTO.getEmail());
        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        userRepository.save(user);
        return jwtService.generateToken(userRequestDTO.getEmail(), user.getId());
    }

    /**
     * Logs in a user by checking if the email and password match the user details in the database.
     * If the email is not found, returns a message indicating that the user is not found.
     * If the password is incorrect, returns a message indicating that the password is incorrect.
     * If the email and password match, returns a token for the user.
     *
     * @param userDTO User details to be checked
     * @return Token if login is successful, message if user is not found or password is incorrect
     */
    public String login(UserRequestDTO userDTO) {
        Users user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new NotFoundException("No user found with this email: " + userDTO.getEmail()));

        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new BadRequestException("Incorrect password");
        }

        return jwtService.generateToken(user.getEmail(), user.getId());
    }


    /**
     * Finds a user by email and returns the user details.
     * If the user is not found, throws a NotFoundException.
     *
     * @param email
     * @return
     */
    public UserResponseDTO findByEmail(String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
        System.out.println(user.getId());

        return usersMapper.toResponseDTO(user);

    }

    /**
     * Updates user information.
     * If the email is changed, a new JWT token is generated and returned.
     *
     * @param userUpdateDTO the updated user information
     * @param userId the ID of the user to update
     * @return a new JWT token if the update is successful
     * @throws NotFoundException if the user does not exist
     */
    public String updateUser(UserUpdateDTO userUpdateDTO, Integer userId) {
        System.out.print(userUpdateDTO);
        System.out.print(userId);
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (userUpdateDTO.getUsername() != null) user.setUsername(userUpdateDTO.getUsername());
        if (userUpdateDTO.getEmail() != null) user.setEmail(userUpdateDTO.getEmail());
        if (userUpdateDTO.getPassword() != null && !userUpdateDTO.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userUpdateDTO.getPassword()));
        }

        userRepository.save(user);

        return jwtService.generateToken(user.getEmail(), user.getId());
    }


}
