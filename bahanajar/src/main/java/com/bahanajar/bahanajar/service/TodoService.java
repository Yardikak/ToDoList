package com.bahanajar.bahanajar.service;

import com.bahanajar.bahanajar.dto.TodoRequest;
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

    // Create
    public Todo createTodo(User user, TodoRequest request) {
        Todo todo = Todo.builder()
                .title(request.title())
                .description(request.description())
                .user(user) // Set pemilik todo
                .build();
        
        return todoRepository.save(todo);
    }

    // Read (Get My Todos)
    public List<Todo> getUserTodos(User user) {
        return todoRepository.findByUserId(user.getId());
    }
}