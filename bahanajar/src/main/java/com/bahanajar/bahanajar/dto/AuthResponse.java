package com.bahanajar.bahanajar.dto;

public record AuthResponse(
        String name,
        String role,
        String token) {
}