package com.example.task_manager.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.task_manager.Entities.Todo;
import com.example.task_manager.Entities.User;
import com.example.task_manager.Repositories.TodoRepository;
import com.example.task_manager.Repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;





@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TodoRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void TodoRepository_SaveTodo_ReturnSavedTodo() {
        
        User user = new User("john_doe", "securePassword123", "john.doe@example.com");
        User savedUser = userRepository.save(user); 

        Todo todo = new Todo(
            "Complete project",
            "Finish all tasks before deadline",
            LocalDate.of(2024, 12, 11),
            false,
            savedUser
        );

        
        Todo savedTodo = todoRepository.save(todo);

        // Asert
        assertNotNull(savedTodo);
        Assertions.assertThat(savedTodo.getId()).isGreaterThan(0);
        assertEquals("Complete project", savedTodo.getTitle());
        assertEquals(savedUser.getId(), savedTodo.getUser().getId());
    }

    @Test
    public void TodoRepository_FindByUserIdOrderByDueDateAsc_ReturnTodos() {
        // Arrange
        User user = new User("jane_doe", "securePassword456", "jane.doe@example.com");
        User savedUser = userRepository.save(user);

        Todo todo1 = new Todo(
            "Task 1",
            "First task",
            LocalDate.of(2024, 12, 10),
            false,
            savedUser
        );

        Todo todo2 = new Todo(
            "Task 2",
            "Second task",
            LocalDate.of(2024, 12, 12),
            true,
            savedUser
        );

        todoRepository.save(todo1);
        todoRepository.save(todo2);

        
        List<Todo> todos = todoRepository.findByUserIdOrderByDueDateAsc(savedUser.getId());

        // Asser
        Assertions.assertThat(todos).isNotNull();
        assertEquals(2, todos.size());
        assertEquals(todo2.getId(), todos.get(1).getId());
        assertEquals("Task 1", todos.get(0).getTitle()); // Ordered by due date
        assertEquals("Task 2", todos.get(1).getTitle());
    }
}
