package com.bahanajar.bahanajar.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor // Lombok: Otomatis buat constructor untuk Dependency Injection
public class JwtAuthenticationFilter extends OncePerRequestFilter { // <-- INHERITANCE

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; // Interface bawaan Spring Security

    @Override // <-- POLIMORFISME
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 1. Cek apakah ada header Authorization dan formatnya "Bearer <token>"
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Lanjut ke filter berikutnya (next)
            return;
        }

        // 2. Ambil tokennya
        jwt = authHeader.substring(7);

        // 3. Ambil email dari token
        userEmail = jwtService.extractUsername(jwt);

        // 4. Jika email ada DAN user belum terautentikasi di SecurityContext saat ini
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Ambil data user dari database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // Validasi token
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // Bikin objek Authentication (KTP)
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                // SIMPAN KE CONTEXT (Ini setara req.user = user di Express)
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 5. Lanjut (next)
        filterChain.doFilter(request, response);
    }
}