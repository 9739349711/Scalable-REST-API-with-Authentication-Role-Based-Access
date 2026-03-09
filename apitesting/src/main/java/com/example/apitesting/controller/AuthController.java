package com.example.apitesting.controller;

import com.example.apitesting.model.User;
import com.example.apitesting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.apitesting.dto.LoginRequest;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*") // Allows requests from any origin
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request){

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(passwordEncoder.matches(request.getPassword(), user.getPassword())){
            return "Login Successful";
        }else{
            return "Invalid Email or Password";
        }
    }
}


