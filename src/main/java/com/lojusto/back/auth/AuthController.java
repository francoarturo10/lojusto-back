package com.lojusto.back.auth;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.lojusto.back.entities.User;
import com.lojusto.back.repositories.UserRepository;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public User register(@RequestBody User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @GetMapping("/me")
    public Map<String, String> getMe(Authentication authentication) {
        // 1. Buscamos al usuario en la base de datos usando el repositorio
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Map<String, String> userDetails = new HashMap<>();
        
        // 2. Agregamos todos los campos que necesites
        // userDetails.put("id", user.getId().toString());
        userDetails.put("username", user.getUsername());
        userDetails.put("firstname", user.getFirstname());
        userDetails.put("lastname", user.getLastname());
        userDetails.put("role", user.getRole().name()); // Usamos el enum del usuario
        userDetails.put("enabled", String.valueOf(user.isEnabled()));
        
        return userDetails;
    }

}