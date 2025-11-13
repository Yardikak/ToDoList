package com.bahanajar.bahanajar.dto;

import jakarta.validation.constraints.NotBlank;

public record TodoRequest(
    @NotBlank(message = "Judul tidak boleh kosong")
    String title,
    String description
) {}