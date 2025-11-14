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

    // REGISTER
    public AuthResponse register(RegisterRequest request) {
        // 1. Buat object User baru
        var user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        // 2. Simpan ke Database
        userRepository.save(user);

        // 3. Generate Token JWT otomatis agar user langsung login setelah register
        var jwtToken = jwtService.generateToken(user);

        // 4. Kembalikan token
        return new AuthResponse(jwtToken);
    }

    // LOGIN
    public AuthResponse login(LoginRequest request) {
        // 1. Autentikasi user (Cek username & password)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()));

        // 2. Jika lolos (password benar), ambil data user dari DB
        var user = userRepository.findByEmail(request.email())
                .orElseThrow();

        // 3. Buat Token baru
        var jwtToken = jwtService.generateToken(user);

        // 4. Kembalikan token
        return new AuthResponse(jwtToken);
    }
}