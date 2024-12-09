package com.example.task_manager.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.task_manager.Entities.Todo;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUserIdOrderByDueDateAsc(Long userId);
}
