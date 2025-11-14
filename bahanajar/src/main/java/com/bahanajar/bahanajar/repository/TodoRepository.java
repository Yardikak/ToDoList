package com.bahanajar.bahanajar.repository;

import com.bahanajar.bahanajar.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// INHERITANCE: Mewarisi semua method CRUD (save, findById, findAll, dll) dari JpaRepository.
// 
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // ABSTRACTION: Anda hanya mendefinisikan fungsinya, Spring yang menulis SQL-nya.
    List<Todo> findByUserId(Long userId);
}