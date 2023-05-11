package com.example.weatherservice.controller;

import com.example.weatherservice.dto.BaseResponseDto;
import com.example.weatherservice.dto.LoginDto;
import com.example.weatherservice.dto.UserCreateDto;
import com.example.weatherservice.entity.UserEntity;
import com.example.weatherservice.service.user.UserService;
import com.example.weatherservice.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public UserEntity login(@RequestBody LoginDto loginDto){
        return userService.signIn(loginDto);
    }

    @PostMapping("/register")
    public BaseResponseDto<UserEntity> register(@RequestBody UserCreateDto userCreateDto){
        return userService.create(userCreateDto);
    }
}
