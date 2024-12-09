package com.example.task_manager.Controller;

import com.example.task_manager.Entities.Todo;
import com.example.task_manager.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/{userId}")
    public Todo addTodo(@PathVariable Long userId, @RequestBody Todo todo) {
        return todoService.addTodo(userId, todo);
    }

    @GetMapping("/{userId}")
    public List<Todo> getTodos(@PathVariable Long userId) {
        return todoService.getTodosByUser(userId);
    }

    @PutMapping("/{todoId}")
    public Todo updateTodo(@PathVariable Long todoId, @RequestBody Todo todo) {
        return todoService.updateTodo(todoId, todo);
    }

    @DeleteMapping("/{todoId}")
    public void deleteTodoById(@PathVariable Long todoId) {
        todoService.deleteTodoById(todoId);
    }
}
