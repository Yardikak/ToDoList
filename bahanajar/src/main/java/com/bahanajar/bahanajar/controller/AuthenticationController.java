package com.bahanajar.bahanajar.controller;

import com.bahanajar.bahanajar.dto.AuthResponse;
import com.bahanajar.bahanajar.dto.LoginRequest;
import com.bahanajar.bahanajar.dto.RegisterRequest;
import com.bahanajar.bahanajar.service.AuthenticationService;
import jakarta.validation.Valid; // <-- Import untuk validasi
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }
}