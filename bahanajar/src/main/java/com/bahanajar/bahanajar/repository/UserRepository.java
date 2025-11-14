package com.bahanajar.bahanajar.repository;

import com.bahanajar.bahanajar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// INHERITANCE: Kita mewarisi SEMUA fitur CRUD canggih milik JpaRepository
// ABSTRACTION: Kita tidak perlu coding SQL manual. Spring yang urus.
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}