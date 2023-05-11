package com.example.weatherservice.service.user;

import com.example.weatherservice.dto.BaseResponseDto;
import com.example.weatherservice.dto.LoginDto;
import com.example.weatherservice.dto.UserCreateDto;
import com.example.weatherservice.entity.CityEntity;
import com.example.weatherservice.entity.UserEntity;
import com.example.weatherservice.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService extends BaseService<UserEntity, UserCreateDto> {

    UserEntity signIn(LoginDto loginDto);

    List<CityEntity> getAllMySubscriptCities(UUID uuid);

    int subscribeToCity(UUID userId, UUID cityId);

    List<UserEntity> getAllUsers();

    int editUserRole(UUID userId, String role);

    int deleteFromSubscription(UUID userId, UUID cityId);

    CityEntity getCityByIdFromUserSub(UUID userId, UUID cityId);

    int deleteCityInAllUsers(UUID cityId);
}
