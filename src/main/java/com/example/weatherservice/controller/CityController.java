package com.example.weatherservice.controller;

import com.example.weatherservice.dto.BaseResponseDto;
import com.example.weatherservice.dto.CreateCityDto;
import com.example.weatherservice.dto.EditCityDto;
import com.example.weatherservice.entity.CityEntity;
import com.example.weatherservice.service.city.CityService;
import com.example.weatherservice.service.city.CityServiceImpl;
import com.example.weatherservice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/city")
public class CityController {

    private final CityService cityService;
    private final UserService userService;

    @PostMapping("/add-city")
    public BaseResponseDto<CityEntity> add(@RequestBody CreateCityDto createCityDto){
        return cityService.create(createCityDto);
    }

    @GetMapping("/all-cities")
    public List<CityEntity> getAll(){
        return cityService.getAll();
    }

    @GetMapping("/all-cities-for-user")
    public List<CityEntity> getAllCitiesForUser(){
        return cityService.getAllCitiesForUser();
    }

    @PutMapping("/edit-city/{id}")
    public int editCity(@PathVariable String id, @RequestBody EditCityDto editCityDto){
        return cityService.edit(UUID.fromString(id), editCityDto);
    }

    @PutMapping("/update-city-weather/{id}")
    public int updateCityWeather(@PathVariable String id, @RequestParam Double temperature){
        return cityService.updateCityWeather(UUID.fromString(id), temperature);
    }

    @DeleteMapping("/delete-city/{id}")
    public int deleteCity(@PathVariable String id){
        userService.deleteCityInAllUsers(UUID.fromString(id));
        return cityService.delete(UUID.fromString(id));
    }

}
