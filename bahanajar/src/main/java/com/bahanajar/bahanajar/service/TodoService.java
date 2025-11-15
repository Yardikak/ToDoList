package com.bahanajar.bahanajar.service;

import com.bahanajar.bahanajar.dto.TodoRequest;
import com.bahanajar.bahanajar.model.Role;
import com.bahanajar.bahanajar.model.Todo;
import com.bahanajar.bahanajar.model.User;
import com.bahanajar.bahanajar.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public Todo createTodo(User user, TodoRequest request) {
        Todo newTodo = Todo.builder()
                .title(request.title())
                .description(request.description())
                .user(user)
                .build();
        // ABSTRACTION: Memanggil method save tanpa peduli query SQL-nya
        return todoRepository.save(newTodo);
    }

    public List<Todo> getUserTodos(User user) {
        if (user.getRole() == Role.ADMIN) {
            // ADMIN
            return todoRepository.findAll();
        }
        // ABSTRACTION: Memanggil method findByUserId tanpa menulis SQL.
        return todoRepository.findByUserId(user.getId());
    }

    public Todo toggleTodoStatus(Long todoId, User user) {
        Todo existingTodo = todoRepository.findById(todoId)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        // ENCAPSULATION: Memastikan yang mengubah status hanyalah pemilik Todo.
        // if (!existingTodo.getUser().getId().equals(user.getId())) {
        // throw new RuntimeException("Access denied");
        // }

        existingTodo.setCompleted(!existingTodo.isCompleted());
        return todoRepository.save(existingTodo);
    }

    public void deleteTodo(Long todoId, User user) {
        Todo todoToDelete = todoRepository.findById(todoId)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        // ENCAPSULATION: Memastikan hanya pemilik yang bisa menghapus.
        // if (!todoToDelete.getUser().getId().equals(user.getId())) {
        // throw new RuntimeException("Access denied");
        // }

        todoRepository.delete(todoToDelete);
    }
}