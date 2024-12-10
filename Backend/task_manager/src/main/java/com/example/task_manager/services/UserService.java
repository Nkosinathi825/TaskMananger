package com.example.task_manager.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.task_manager.Dto.LoginDto;
import com.example.task_manager.Dto.RegisterDto;
import com.example.task_manager.Entities.User;
import com.example.task_manager.Repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(RegisterDto registerDto) {
        if (userRepository.findByEmail(registerDto.getEmail()) != null) {
            throw new RuntimeException("Email exists");
        }else{
            String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
            User user = new User(registerDto.getUsername(), encodedPassword, registerDto.getEmail());
            return userRepository.save(user);
        }

    }

    public User loginUser(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail());
        if (user == null) {
            throw new RuntimeException("User not found");
        }else{
            if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                return user;
                
            }else{
                throw new RuntimeException("Invalid credentials");
            }
    
           
        }


        
    }
}
