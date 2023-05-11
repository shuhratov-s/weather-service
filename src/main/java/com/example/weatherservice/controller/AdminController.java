package com.example.weatherservice.controller;

import com.example.weatherservice.entity.CityEntity;
import com.example.weatherservice.entity.UserEntity;
import com.example.weatherservice.service.user.UserService;
import com.example.weatherservice.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final UserService userService;

    @GetMapping("/all-users")
    public List<UserEntity> getAll(){
        return userService.getAllUsers();
    }

    @GetMapping("/user-details/{id}")
    public List<CityEntity> userDetails(@PathVariable String id){
        return userService.getAllMySubscriptCities(UUID.fromString(id));
    }

    @PutMapping("/edit-user/{id}")
    public int editUser(@PathVariable String id, @RequestParam String role){
        return userService.editUserRole(UUID.fromString(id), role);
    }
}
