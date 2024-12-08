package com.example.task_manager.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.task_manager.Dto.LoginDto;
import com.example.task_manager.Dto.RegisterDto;
import com.example.task_manager.Entities.User;
import com.example.task_manager.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterDto registerDto) {
        return userService.registerUser(registerDto);
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginDto loginDto) {
        return userService.loginUser(loginDto);
    }
}
