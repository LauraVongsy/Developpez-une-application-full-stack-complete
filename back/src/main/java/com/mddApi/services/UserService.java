package com.mddApi.services;



import com.mddApi.dtos.UserRequestDTO;
import com.mddApi.models.UserPrincipal;
import com.mddApi.models.Users;
import com.mddApi.repositories.UserRepository;
import com.mddApi.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    /**
     * Method used within Spring Security to retrieve user details from the database using email.
     * If user exists, returns the details wrapped in a UserPrincipal object for Spring Security authentication process.
     *
     * @param email Email of the user
     * @return User if found in database
     * @throws UsernameNotFoundException User not found exception from Spring Security
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Users> user = userRepository.findByEmail(email);

        return user.map(UserPrincipal::new).orElseThrow(() -> new UsernameNotFoundException("User email not found " + email));
    }

    public String register(UserRequestDTO userRequestDTO) {
        System.out.print( userRequestDTO);
        if (userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            return "This email is already in use";
        }

        Users user = new Users();
        user.setEmail(userRequestDTO.getEmail());
        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        userRepository.save(user);
        return jwtService.generateToken(userRequestDTO.getEmail(), user.getId());
    }
}
