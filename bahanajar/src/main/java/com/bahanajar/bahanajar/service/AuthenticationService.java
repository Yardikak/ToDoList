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

    // LOGIKA REGISTER
    public AuthResponse register(RegisterRequest request) {
        // 1. Buat object User baru (pake Builder Pattern biar rapi)
        var user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password())) // Hash password!
                .role(Role.USER) // Default role user biasa
                .build();

        // 2. Simpan ke Database
        userRepository.save(user);

        // 3. Generate Token JWT otomatis agar user langsung login setelah register
        var jwtToken = jwtService.generateToken(user);

        // 4. Kembalikan token
        return new AuthResponse(jwtToken);
    }

    // LOGIKA LOGIN
    public AuthResponse login(LoginRequest request) {
        // 1. Autentikasi user (Cek username & password)
        // Ini akan otomatis gagal (throw exception) jika password salah
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()));

        // 2. Jika lolos (password benar), ambil data user dari DB
        var user = userRepository.findByEmail(request.email())
                .orElseThrow(); // Throw error jika user entah kenapa tidak ketemu

        // 3. Buat Token baru
        var jwtToken = jwtService.generateToken(user);

        // 4. Kembalikan token
        return new AuthResponse(jwtToken);
    }
}