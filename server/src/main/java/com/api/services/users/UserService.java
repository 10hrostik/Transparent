package com.api.services.users;

import com.api.controllers.dto.user.ResponseUserDto;
import com.api.controllers.dto.user.UserMapper;
import com.api.entities.user.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService extends GeneralUserService {
    public Mono<ResponseUserDto> editUser(String credentials, String newPassword) {
        Mono<User> userMono = findByUsername(credentials).cast(User.class);
        userMono.flatMap(x -> userRepository
                .updatePassword(passwordEncoder.encode(newPassword), x.getId())).subscribe();

        return userMono.map(UserMapper::entityToResponseDto);
    }
}
