package com.example.weatherservice.controller;

import com.example.weatherservice.entity.CityEntity;
import com.example.weatherservice.service.user.UserService;
import com.example.weatherservice.service.user.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/get-subscriptions/{id}")
    public List<CityEntity> getSubscriptions(@PathVariable String id){
        return userService.getAllMySubscriptCities(UUID.fromString(id));
    }

    @PostMapping("/subscribe-to-city/{userId}/{cityId}")
    public int subscribeToCity(@PathVariable String userId, @PathVariable String cityId){
        return userService.subscribeToCity(UUID.fromString(userId), UUID.fromString(cityId));
    }

    @DeleteMapping("/delete-from-subscription/{userId}/{cityId}")
    public int deleteFromSubscription(@PathVariable String userId, @PathVariable String cityId){
        return userService.deleteFromSubscription(UUID.fromString(userId), UUID.fromString(cityId));
    }

    @DeleteMapping("/delete/{id}")
    public int deleteUser(@PathVariable String id){
        return userService.delete(UUID.fromString(id));
    }
}
