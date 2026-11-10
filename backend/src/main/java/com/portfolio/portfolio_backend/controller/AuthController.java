package com.portfolio.portfolio_backend.controller;

import com.portfolio.portfolio_backend.model.User;
import com.portfolio.portfolio_backend.repository.UserRepository;
import com.portfolio.portfolio_backend.security.JwtService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final UserRepository repo;
    private final JwtService jwtService;

    public AuthController(UserRepository repo, JwtService jwtService) {
        this.repo = repo;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {

        User dbUser = repo.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!dbUser.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(dbUser.getUsername());

        return Map.of(
                "token", token,
                "role", dbUser.getRole()
        );
    }
}