package com.example.task_manager.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.task_manager.Entities.User;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email); 
}
