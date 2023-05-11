package com.example.weatherservice.service.city;

import com.example.weatherservice.dto.CreateCityDto;
import com.example.weatherservice.dto.EditCityDto;
import com.example.weatherservice.entity.CityEntity;
import com.example.weatherservice.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CityService extends BaseService<CityEntity, CreateCityDto> {

    int edit(UUID id, EditCityDto editCityDto);

    int updateCityWeather(UUID id, Double temperature);

    List<CityEntity> getAllCitiesForUser();
}
