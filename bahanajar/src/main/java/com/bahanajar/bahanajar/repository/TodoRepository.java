package com.bahanajar.bahanajar.repository;

import com.bahanajar.bahanajar.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    // Cari semua Todo milik user ID tertentu
    List<Todo> findByUserId(Long userId);
}