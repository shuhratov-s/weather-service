package com.example.weatherservice.service;

import com.example.weatherservice.dto.BaseResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public interface BaseService<T, CD> {

    BaseResponseDto<T> create(CD cd);

    T getById(UUID id);

    List<T> getAll();

    int delete(UUID id);
}
