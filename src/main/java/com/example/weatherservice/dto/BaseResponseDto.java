package com.example.weatherservice.dto;

import lombok.*;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponseDto<T> {
    private Optional<T> t;

    private int status;
}
