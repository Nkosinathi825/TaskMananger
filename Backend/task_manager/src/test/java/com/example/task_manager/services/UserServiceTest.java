package com.example.task_manager.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.task_manager.Dto.LoginDto;
import com.example.task_manager.Dto.RegisterDto;
import com.example.task_manager.Entities.User;
import com.example.task_manager.Repositories.UserRepository;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void UserService_registerUser_ShouldRegisterNewUser() {
        // Arrange
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("john_doe");
        registerDto.setPassword("password123");
        registerDto.setEmail("test@example.com");
        when(userRepository.findByEmail(registerDto.getEmail())).thenReturn(null);
        when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");
        User savedUser = new User("john_doe", "encodedPassword", "test@example.com");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        
        User result = userService.registerUser(registerDto);

        // Asert
        assertNotNull(result);
        assertEquals("john_doe", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("encodedPassword", result.getPassword());
        verify(userRepository, times(1)).findByEmail(registerDto.getEmail());
    }

    @Test
    void UserService_registerUser_ThrowException_EmailAlreadyExists() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("testUser");
        registerDto.setPassword("password123");
        registerDto.setEmail("test@example.com");
        when(userRepository.findByEmail(registerDto.getEmail())).thenReturn(new User());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(registerDto);
        });
        assertEquals("Email exists", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void UserService_loginUser_ShouldReturnUser() {

        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@example.com");
        loginDto.setPassword("password123");

        User user = new User("testUser", "encodedPassword", "test@example.com");
        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(true);

        User result = userService.loginUser(loginDto);

        // Asert
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
    }

    @Test
    void UserService_loginUser_ThrowException_UserNotFound() {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@example.com");
        loginDto.setPassword("password123");

        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.loginUser(loginDto);
        });
        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(loginDto.getEmail());
    }

    @Test
    void UserService_loginUser_ThrowException_PasswordIsInvalid() {
        LoginDto loginDto = new LoginDto();
        loginDto.setEmail("test@example.com");
        loginDto.setPassword("password123");

        User user = new User("testUser", "encodedPassword", "test@example.com");
        when(userRepository.findByEmail(loginDto.getEmail())).thenReturn(user);
        when(passwordEncoder.matches(loginDto.getPassword(), user.getPassword())).thenReturn(false);

        // act & assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.loginUser(loginDto);
        });
        assertEquals("Invalid credentials", exception.getMessage());
    }
}
