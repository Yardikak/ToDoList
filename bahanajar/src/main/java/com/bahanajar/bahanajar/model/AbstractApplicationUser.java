package com.bahanajar.bahanajar.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass; // Menyediakan field tanpa membuat tabel
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

// MAPPEDSUPERCLASS: Field-field di sini akan diwariskan ke tabel anak (Admin/Pengguna)
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class AbstractApplicationUser { // OOP: ABSTRACT CLASS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password; // Encapsulation: Password still protected
    private String name;

    // Method abstrak: Wajib diimplementasikan oleh setiap Subclass
    public abstract String getRoleName();
}