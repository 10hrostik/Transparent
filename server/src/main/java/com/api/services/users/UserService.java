package com.api.services.users;

import com.api.controllers.dto.users.EditUserProfileDto;
import com.api.controllers.dto.users.ResponseUserDto;
import com.api.controllers.mappers.UserMapper;
import com.api.entities.users.User;
import com.api.repositories.CountryRepository;
import com.api.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService extends GeneralUserService {
    public UserService(UserRepository userRepository, CountryRepository countryRepository,
                       PasswordEncoder passwordEncoder, UserMapper userMapper) {
        super(userRepository, countryRepository, passwordEncoder, userMapper);
    }

    public Mono<ResponseUserDto> editUserPassword(String credentials, String newPassword) {
        Mono<User> userMono = findByUsername(credentials).cast(User.class);
        userMono.flatMap(user -> userRepository
                .updatePassword(passwordEncoder.encode(newPassword), user.getId())).subscribe();

        return userMono.map(userMapper::entityToResponseDto);
    }

    public Mono<ResponseUserDto> editUser(EditUserProfileDto userProfileDto) {
        return userRepository.findById(userProfileDto.getId()).flatMap(user -> {
            if (user.getId() != null) {
                return userRepository.save(userMapper.editUser(userProfileDto, user));
            }
            return Mono.empty();
        }).map(userMapper::entityToResponseDto);
    }
}
