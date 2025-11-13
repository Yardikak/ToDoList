package com.bahanajar.bahanajar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Email wajib diisi") @Email(message = "Format email salah") String email,

        @NotBlank(message = "Password wajib diisi") String password) {
}