package com.bahanajar.bahanajar.config;

// 1. IMPORT HANDLER BARU ANDA
import com.bahanajar.bahanajar.security.CustomAccessDeniedHandler;
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

        // 2. INJEKSI HANDLER BARU ANDA (Akan otomatis diisi oleh
        // @RequiredArgsConstructor)
        private final CustomAccessDeniedHandler customAccessDeniedHandler;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                // Matikan CSRF
                                .csrf(csrf -> csrf.disable())

                                // Atur Izin Akses (Routing)
                                .authorizeHttpRequests(auth -> auth
                                                // Izinkan akses ke Auth dan Swagger TANPA token
                                                .requestMatchers(
                                                                "/api/auth/**",
                                                                "/v3/api-docs/**",
                                                                "/swagger-ui/**",
                                                                "/swagger-ui.html")
                                                .permitAll()
                                                // Endpoint lain WAJIB authenticated
                                                .anyRequest().authenticated())

                                // 3. TAMBAHKAN BLOK INI UNTUK MENANGANI ERROR 403
                                .exceptionHandling(ex -> ex
                                                .accessDeniedHandler(customAccessDeniedHandler))

                                // Atur Session jadi STATELESS
                                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                                // Set Provider autentikasi kita
                                .authenticationProvider(authenticationProvider)

                                // Pasang Filter JWT kita SEBELUM filter bawaan
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}