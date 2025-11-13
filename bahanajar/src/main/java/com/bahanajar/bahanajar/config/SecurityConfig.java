package com.bahanajar.bahanajar.config;

import com.bahanajar.bahanajar.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Matikan CSRF (karena kita pakai JWT / Stateless API, bukan Session browser)
                .csrf(csrf -> csrf.disable())

                // Atur Izin Akses (Routing)
                .authorizeHttpRequests(auth -> auth
                        // Izinkan akses ke /api/auth/** (Login/Register) TANPA token
                        .requestMatchers("/api/auth/**").permitAll()
                        // --- IZIN SWAGGER UI (TAMBAHKAN INI) ---
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html")
                        .permitAll()
                        // Endpoint lain WAJIB authenticated
                        .anyRequest().authenticated())

                // Atur Session jadi STATELESS (karena pakai JWT)
                // Artinya server tidak nyimpan sesi user di memori (hemat RAM)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Set Provider autentikasi kita
                .authenticationProvider(authenticationProvider)

                // Pasang Filter JWT kita SEBELUM filter bawaan
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}