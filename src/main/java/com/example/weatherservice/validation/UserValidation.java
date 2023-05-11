package com.example.weatherservice.validation;


import com.example.weatherservice.dto.UserCreateDto;
import com.example.weatherservice.entity.UserEntity;

import java.util.List;
import java.util.Objects;

public class UserValidation {

    public static boolean checkUsername(String username, List<UserEntity> allUsers) {
        for (UserEntity userEntity : allUsers) {
            if (Objects.equals(userEntity.getUsername(), username)){
                return true;
            }
        }
        return false;
    }

    public static boolean checkUser(UserCreateDto createDto) {
        String fullName = createDto.getFullName();
        String username = createDto.getUsername();
        String password = createDto.getPassword();

        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() ||
                fullName.isBlank() || username.isBlank() || password.isBlank()){
            return false;
        }
        return true;
    }
}
