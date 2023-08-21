package com.api.services.users;

import com.api.controllers.dto.user.RegisterUserDto;
import com.api.controllers.dto.user.ResponseUserDto;
import com.api.controllers.dto.user.UserMapper;
import com.api.entities.user.User;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class AuthService extends GeneralUserService implements ReactiveUserDetailsService {
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return super.findByUsername(username);
    }

    public Mono<ResponseUserDto> register(RegisterUserDto userDto) {
        return userRepository.save(UserMapper.registerDtoToEntity(userDto)).map(UserMapper::entityToResponseDto);
    }

    public Mono<ResponseUserDto> getUser(Object credential, String password) throws IllegalArgumentException {
        Mono<User> user;
        if (credential instanceof Long) {
            user = getUserByPhone((Long) credential);
            user.flatMap(this::updateLoginDate).subscribe();

            return user.flatMap(x -> countryRepository.findById(x.getCountryId())
                            .map(y -> {
                                x.setCountry(y);
                                return x;
                            })
                    )
                    .map(UserMapper::entityToResponseDto);
        } else if (credential instanceof String) {
            password = passwordEncoder.encode(password);
            user = getUserByCredentialAndPassword((String) credential, password);
            user.flatMap(this::updateLoginDate).subscribe();

            return user.flatMap(x -> countryRepository.findById(x.getCountryId())
                            .map(y -> {
                                x.setCountry(y);
                                return x;
                            })
                    )
                    .map(UserMapper::entityToResponseDto);
        }
        throw new IllegalArgumentException("Wrong input, check credentials!");
    }

    private Mono<User> getUserByPhone(Long phone) {
        return userRepository.findByPhone(phone);
    }

    private Mono<User> getUserByCredentialAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
