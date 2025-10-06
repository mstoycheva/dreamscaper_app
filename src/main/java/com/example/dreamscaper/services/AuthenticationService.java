package com.example.dreamscaper.services;

import com.example.dreamscaper.models.User;
import com.example.dreamscaper.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

//    public boolean signIn(String username, String password) {
//        User user = userRepository.findByUsername(username);
//        if (user != null) {
//            return passwordEncoder.matches(password, user.getPassword());
//        }
//        return false;
//    }


}
