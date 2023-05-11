package com.example.weatherservice.service.user;

import com.example.weatherservice.common.AuthenticationException;
import com.example.weatherservice.dto.BaseResponseDto;
import com.example.weatherservice.dto.LoginDto;
import com.example.weatherservice.dto.UserCreateDto;
import com.example.weatherservice.entity.CityEntity;
import com.example.weatherservice.entity.UserEntity;
import com.example.weatherservice.entity.UserRole;
import com.example.weatherservice.common.DataNotFoundException;
import com.example.weatherservice.repository.UserRepository;
import com.example.weatherservice.service.city.CityServiceImpl;
import com.example.weatherservice.validation.UserValidation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CityServiceImpl cityServiceImpl;
    private final PasswordEncoder passwordEncoder;

    @Override
    public BaseResponseDto<UserEntity> create(UserCreateDto createDto) {

        if (!UserValidation.checkUser(createDto)){
            return new BaseResponseDto<>(Optional.empty(), 402); // fullName, username or password is empty or blank
        }

        if (UserValidation.checkUsername(createDto.getUsername(), getAll())){
            return new BaseResponseDto<>(Optional.empty(), 403); // this  is already exists
        }

        Set<UserRole> roles = new HashSet<>();
        roles.add(UserRole.USER);

        UserEntity map = modelMapper.map(createDto, UserEntity.class);
        map.setRoles(roles);
        map.setPassword(passwordEncoder.encode(createDto.getPassword()));

        UserEntity user = userRepository.save(map);

        return new BaseResponseDto<>(Optional.of(user), 201);
    }

    @Override
    public UserEntity signIn(LoginDto loginDto) {

        Optional<UserEntity> user = userRepository.findUserEntityByUsername(loginDto.getUsername());
        UserEntity userEntity = user.orElseThrow(() -> new AuthenticationException("password or username is wrong"));

        if (passwordEncoder.matches(userEntity.getPassword(), loginDto.getPassword())) {
            return userEntity;
        }

        throw new AuthenticationException("password or username is wrong");
    }

    @Override
    public UserEntity getById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("user not found")
        );
    }

    @Override
    public List<UserEntity> getAll(){
        return userRepository.findAll();
    }

    @Override
    public List<CityEntity> getAllMySubscriptCities(UUID uuid) {
        UserEntity user = getById(uuid);
        return user.getSubscriptions();
    }

    @Override
    public int subscribeToCity(UUID userId, UUID cityId) {
        UserEntity user = getById(userId);
        CityEntity city = cityServiceImpl.getById(cityId);

        if (checkCityFromSubscriptions(user, city.getId())){
            return 403; // this city is already exists in your subscriptions
        }

        user.getSubscriptions().add(city);
        userRepository.save(user);
        return 200;
    }

    private boolean checkCityFromSubscriptions(UserEntity user, UUID cityId) {
        for (CityEntity subscription : user.getSubscriptions()) {
            if (Objects.equals(subscription.getId(), cityId)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        List<UserEntity> users = new ArrayList<>();
        for (UserEntity user : getAll()) {
            for (UserRole role : user.getRoles()) {
                if (Objects.equals(role, UserRole.USER)){
                    users.add(user);
                }
            }
        }
        return users;
    }

    @Override
    public int editUserRole(UUID userId, String role) {
        UserEntity user = getById(userId);

        UserRole userRole;
        try {
            userRole = UserRole.valueOf(role);
        }catch (Exception e){
            return 500; // no such role
        }

        user.getRoles().add(userRole);
        userRepository.save(user);
        return 200;
    }

    @Override
    public int deleteFromSubscription(UUID userId, UUID cityId) {
        UserEntity user = getById(userId);
        user.getSubscriptions().remove(getCityByIdFromUserSub(userId, cityId));
        userRepository.save(user);
        return 200;
    }

    @Override
    public CityEntity getCityByIdFromUserSub(UUID userId, UUID cityId){
        UserEntity user = getById(userId);

        for (CityEntity subscription : user.getSubscriptions()) {
            if (Objects.equals(cityId, subscription.getId())){
                return subscription;
            }
        }

        throw new DataNotFoundException("there is no such city in your subscription");
    }

    @Override
    public int delete(UUID id) {
        userRepository.delete(getById(id));
        return 209;
    }

    @Override
    public int deleteCityInAllUsers(UUID cityId) {
        CityEntity city = cityServiceImpl.getById(cityId);

        for (UserEntity user : getAll()) {
            user.getSubscriptions().remove(city);
            userRepository.save(user);
        }
        return 200;
    }
}
