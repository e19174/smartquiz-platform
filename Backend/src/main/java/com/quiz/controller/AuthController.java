package com.quiz.controller;

import com.quiz.dto.LoginRequest;
import com.quiz.entity.Admin;
import com.quiz.repository.AdminRepository;
import com.quiz.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AdminRepository adminRepository;
    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder encoder =
            new BCryptPasswordEncoder();

    public AuthController(
            AdminRepository adminRepository,
            JwtUtil jwtUtil
    ) {
        this.adminRepository = adminRepository;
        this.jwtUtil = jwtUtil;
    }

    // REGISTER ADMIN
    @PostMapping("/register")
    public String register(@RequestBody LoginRequest request) {

        Admin admin = new Admin();

        admin.setEmail(request.getEmail());

        admin.setPassword(
                encoder.encode(request.getPassword())
        );

        adminRepository.save(admin);

        return "Admin Registered";
    }

    // LOGIN
    @PostMapping("/login")
    public Map<String, String> login(
            @RequestBody LoginRequest request
    ) {

        Admin admin = adminRepository
                .findByEmail(request.getEmail())
                .orElseThrow();

        if (!encoder.matches(
                request.getPassword(),
                admin.getPassword()
        )) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(admin.getEmail());

        return Map.of("token", token);
    }
}