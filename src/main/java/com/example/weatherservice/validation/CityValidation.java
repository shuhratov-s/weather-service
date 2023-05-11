package com.example.weatherservice.validation;

import com.example.weatherservice.dto.CreateCityDto;
import com.example.weatherservice.entity.CityEntity;

import java.util.List;
import java.util.Objects;

public class CityValidation {

    public static boolean checkCity(CreateCityDto createCityDto){
        String name = createCityDto.getName();
        Double longitude = createCityDto.getLongitude();
        Double latitude = createCityDto.getLatitude();
        Double temperature = createCityDto.getTemperature();
        Boolean visibility = createCityDto.getVisibility();

        if (name.isBlank() || name.isEmpty()
                || longitude == null
                || latitude == null
                || temperature == null
                || visibility == null){
            return false;
        }
        return true;
    }

    public static boolean checkCityName(String name, List<CityEntity> cities) {
        for (CityEntity city : cities) {
            if (Objects.equals(city.getName(), name)){
                return true;
            }
        }
        return false;
    }
}
