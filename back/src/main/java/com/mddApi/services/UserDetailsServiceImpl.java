package com.mddApi.services;

import com.mddApi.models.UserPrincipal;
import com.mddApi.models.Users;
import com.mddApi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation of UserDetailsService for Spring Security.
 * This class is responsible for loading user details based on the user's email address
 * to allow Spring Security to perform authentication.
 * When a user attempts to log in, Spring Security uses this service to retrieve
 * the user's information (credentials, roles, etc.).
 */
@Service
public class UserDetailsServiceImpl  implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Users> user = userRepository.findByEmail(email);

        return user.map(UserPrincipal::new).orElseThrow(() -> new UsernameNotFoundException("User email not found " + email));
    }
}
