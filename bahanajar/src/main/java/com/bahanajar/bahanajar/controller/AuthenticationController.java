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
public class AuthenticationController { // <-- INI YANG TADI HILANG

    private final AuthenticationService service; // <-- INI JUGA TADI HILANG

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request // <-- @Valid sudah aman
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request // <-- @Valid sudah aman
    ) {
        return ResponseEntity.ok(service.login(request));
    }
}