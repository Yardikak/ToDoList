package com.bahanajar.bahanajar.controller;

import com.bahanajar.bahanajar.dto.TodoRequest;
import com.bahanajar.bahanajar.model.Todo;
import com.bahanajar.bahanajar.model.User;
import com.bahanajar.bahanajar.service.TodoService;
import io.swagger.v3.oas.annotations.Parameter; // <--- GANTI IMPORT INI
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

    @PostMapping
    public ResponseEntity<Todo> create(
            // Ganti @Hidden jadi @Parameter(hidden = true)
            @Parameter(hidden = true) @AuthenticationPrincipal User user, 
            @Valid @RequestBody TodoRequest request
    ) {
        return ResponseEntity.ok(todoService.createTodo(user, request));
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getAll(
            // Ganti @Hidden jadi @Parameter(hidden = true)
            @Parameter(hidden = true) @AuthenticationPrincipal User user 
    ) {
        return ResponseEntity.ok(todoService.getUserTodos(user));
    }
}