package com.bahanajar.bahanajar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Nama tidak boleh kosong") String name,

        @NotBlank(message = "Email tidak boleh kosong") @Email(message = "Format email salah") String email,

        @NotBlank(message = "Password tidak boleh kosong") @Size(min = 6, message = "Password minimal 6 karakter") String password) {
}