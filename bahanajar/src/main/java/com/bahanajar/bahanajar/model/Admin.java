package com.bahanajar.bahanajar.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users_admin")
public class Admin extends AbstractApplicationUser { // INHERITANCE

    // Admin mungkin punya field khusus, tapi kita biarkan kosong untuk menunjukkan
    // struktur.

    @Override
    public String getRoleName() {
        return "ADMIN";
    }
}