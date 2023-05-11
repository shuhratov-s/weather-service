package com.example.weatherservice.service.city;

import com.example.weatherservice.dto.BaseResponseDto;
import com.example.weatherservice.dto.CreateCityDto;
import com.example.weatherservice.dto.EditCityDto;
import com.example.weatherservice.entity.CityEntity;
import com.example.weatherservice.common.DataNotFoundException;
import com.example.weatherservice.repository.CityRepository;
import com.example.weatherservice.validation.CityValidation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService{

    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    @Override
    public BaseResponseDto<CityEntity> create(CreateCityDto createCityDto) {

        if (!CityValidation.checkCity(createCityDto)){
            return new BaseResponseDto<>(Optional.empty(), 402); // name is empty or blank, temperature, longitude, latitude or visibility is null
        }

        if (CityValidation.checkCityName(createCityDto.getName(), getAll())){
            return new BaseResponseDto<>(Optional.empty(), 403); // this name already exists
        }

        CityEntity city = cityRepository.save(modelMapper.map(createCityDto, CityEntity.class));
        city.setVisibility(createCityDto.getVisibility());
        return new BaseResponseDto<>(Optional.of(city), 201);
    }

    @Override
    public List<CityEntity> getAll() {
        return cityRepository.findAll();
    }

    @Override
    public CityEntity getById(UUID id) {
        return cityRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("city not found")
        );
    }

    @Override
    public int edit(UUID id, EditCityDto editCityDto) {
        CityEntity city = getById(id);
        modelMapper.getConfiguration()
                .setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(editCityDto, city);
        cityRepository.save(city);
        return 200;
    }

    @Override
    public int updateCityWeather(UUID id, Double temperature) {
        CityEntity city = getById(id);

        if (temperature == null) return 500;

        city.setTemperature(temperature);
        cityRepository.save(city);
        return 200;
    }

    @Override
    public int delete(UUID id) {
        CityEntity city = getById(id);

        cityRepository.delete(city);
        return 209;
    }

    @Override
    public List<CityEntity> getAllCitiesForUser() {
        List<CityEntity> cities = new ArrayList<>();

        for (CityEntity city : getAll()) {
            if (city.getVisibility()){
                cities.add(city);
            }
        }

        return cities;
    }
}
