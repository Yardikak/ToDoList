package com.bahanajar.bahanajar.controller;

import com.bahanajar.bahanajar.dto.TodoRequest;
import com.bahanajar.bahanajar.model.Todo;
import com.bahanajar.bahanajar.model.User;
import com.bahanajar.bahanajar.service.TodoService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    // CREATE
    @PostMapping
    public ResponseEntity<Todo> create(
            @Parameter(hidden = true) @AuthenticationPrincipal User user,
            @Valid @RequestBody TodoRequest request // ENCAPSULATION
    ) {
        // ENCAPSULATION
        return ResponseEntity.ok(todoService.createTodo(user, request));
    }

    // READ
    @GetMapping
    public ResponseEntity<List<Todo>> getAll(
            @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        // ENCAPSULATION
        return ResponseEntity.ok(todoService.getUserTodos(user));
    }

    // UPDATE
    @PutMapping("/{id}/toggle")
    public ResponseEntity<Todo> toggleStatus(
            @PathVariable Long id,
            @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        // ENCAPSULATION: Delegasi logika update status ke Service
        Todo updatedTodo = todoService.toggleTodoStatus(id, user);
        return ResponseEntity.ok(updatedTodo);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable Long id,
            @Parameter(hidden = true) @AuthenticationPrincipal User user) {
        // ENCAPSULATION: Delegasi logika delete ke Service
        todoService.deleteTodo(id, user);
        return ResponseEntity.ok().build();
    }
}