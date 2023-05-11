package com.example.weatherservice.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCityDto {

    private String name;

    private Double latitude;

    private Double longitude;

    private Double temperature;

    private Boolean visibility;
}
