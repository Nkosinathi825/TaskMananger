package com.example.task_manager.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.task_manager.Entities.User;
import com.example.task_manager.Repositories.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;

    


    @Test
    public void UserRepository_SaveUser_ReturnSavedUser(){

        //Arange 
        User user = new User("john_doe",  "securePassword123","john.doe@example.com");


        //act 
        User SavedUser =userRepository.save(user);
        User findUser =userRepository.findByEmail("john.doe@example.com");


        //Asert
        Assertions.assertThat(SavedUser).isNotNull();
        Assertions.assertThat(SavedUser.getId()).isGreaterThan(0);

        assertEquals("john_doe", findUser.getUsername());


    }
}
