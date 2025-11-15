package com.bahanajar.bahanajar.service;

import com.bahanajar.bahanajar.dto.AuthResponse;
import com.bahanajar.bahanajar.dto.LoginRequest;
import com.bahanajar.bahanajar.dto.RegisterRequest;
import com.bahanajar.bahanajar.model.Role;
import com.bahanajar.bahanajar.model.User;
import com.bahanajar.bahanajar.repository.UserRepository;
import com.bahanajar.bahanajar.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // REGISTER (Pengguna Default)
    public AuthResponse register(RegisterRequest request) {
        // 1. Buat object User baru
        User user = User.builder() // OOP: Builder Pattern
                .role(Role.USER)
                .email(request.email())
                .name(request.name())
                .password(passwordEncoder.encode(request.password()))
                .build();

        // 2. Simpan ke Database
        userRepository.save(user); // OOP: Abstraction

        // 3. Generate Token JWT
        String jwtToken = jwtService.generateToken(user);

        // 4. Kembalikan token
        return new AuthResponse(jwtToken);
    }

    // REGISTER (Admin)
    public AuthResponse registerAdmin(RegisterRequest request) {
        User user = User.builder()
                .role(Role.ADMIN)
                .email(request.email())
                .name(request.name())
                .password(passwordEncoder.encode(request.password()))
                .build();

        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }

    // LOGIN
    public AuthResponse login(LoginRequest request) {
        // 1. Autentikasi user
        authenticationManager.authenticate( // OOP: Abstraction
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()));

        // 2. Ambil data user dari DB
        var user = userRepository.findByEmail(request.email())
                .orElseThrow();

        // 3. Buat Token baru
        var jwtToken = jwtService.generateToken(user);

        // 4. Kembalikan token
        return new AuthResponse(jwtToken);
    }
}