package com.example.task_manager.services;

import com.example.task_manager.Entities.Todo;
import com.example.task_manager.Entities.User;
import com.example.task_manager.Repositories.TodoRepository;
import com.example.task_manager.Repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TodoService todoService;

    public TodoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void TodoService_addTodo_SavesTodo() {
        Long userId = 1L;
        User user = new User("john_doe", "password", "john.doe@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Todo todo = new Todo("Test Todo", "Description", LocalDate.now(), false, user);
        when(todoRepository.save(todo)).thenReturn(todo);

        Todo result = todoService.addTodo(userId, todo);

    
        verify(todoRepository, times(1)).save(todo);
        assertThat(result).isEqualTo(todo);
    }

    @Test
    void TodoService_addTodo_ThrowsException_UserNotExist() {
        Long userId = 1L;
        Todo todo = new Todo();
        User user = new User("john_doe", "password", "john.doe@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> todoService.addTodo(userId, todo));
        assertThat(exception.getMessage()).isEqualTo("User not found");
    }

    @Test
    void  TodoService_getTodos_ReturnsTodos() {
        Long userId = 1L;
        Todo todo1 = new Todo("Task 1", "Description 1", LocalDate.now(), false, null);
        Todo todo2 = new Todo("Task 2", "Description 2", LocalDate.now().plusDays(1), true, null);

        when(todoRepository.findByUserIdOrderByDueDateAsc(userId)).thenReturn(Arrays.asList(todo1, todo2));

        var result = todoService.getTodosByUser(userId);

        

        assertThat(result).containsExactly(todo1, todo2);
    }

    @Test
    void  TodoService_updateTodo_UpdatesTodo() {
        Long todoId = 1L;
        Todo existingTodo = new Todo("Old Title", "Old Description", LocalDate.now(), false, null);
        Todo updatedTodo = new Todo("New Title", "New Description", LocalDate.now().plusDays(1), true, null);

        when(todoRepository.findById(todoId)).thenReturn(Optional.of(existingTodo));
        when(todoRepository.save(existingTodo)).thenReturn(existingTodo);


        Todo result = todoService.updateTodo(todoId, updatedTodo);

        // Assert
        assertThat(result.getTitle()).isEqualTo(updatedTodo.getTitle());
        assertThat(result.getDescription()).isEqualTo(updatedTodo.getDescription());
        assertThat(result.getDueDate()).isEqualTo(updatedTodo.getDueDate());
        assertThat(result.isCompleted()).isEqualTo(updatedTodo.isCompleted());
    }

    @Test
    void  TodoService_updateTodo_ThrowsException() {
        Long todoId = 1L;
        Todo updatedTodo = new Todo();
        when(todoRepository.findById(todoId)).thenReturn(Optional.empty());

        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> todoService.updateTodo(todoId, updatedTodo));
        assertThat(exception.getMessage()).isEqualTo("Todo not found");
    }

    @Test
    void deleteTodoById_DeletesTodo() {
        // Arrange
        Long todoId = 1L;

        // Act
        todoService.deleteTodoById(todoId);

        // Assert
        verify(todoRepository, times(1)).deleteById(todoId);
    }
}
