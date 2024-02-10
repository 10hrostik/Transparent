package com.api.services.users;

import com.api.controllers.dto.users.EditUserPasswordDto;
import com.api.controllers.dto.users.RegisterUserDto;
import com.api.controllers.dto.users.ResponseUserDto;
import com.api.controllers.mappers.UserMapper;
import com.api.entities.users.User;
import com.api.exceptions.InvalidCredentialException;
import com.api.repositories.CountryRepository;
import com.api.repositories.UserRepository;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class AuthService extends GeneralUserService implements ReactiveUserDetailsService {
    public AuthService(UserRepository userRepository, CountryRepository countryRepository,
                       PasswordEncoder passwordEncoder, UserMapper userMapper) {
        super(userRepository, countryRepository, passwordEncoder, userMapper);
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return super.findByUsername(username);
    }

    public Mono<ResponseUserDto> register(RegisterUserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return findByUsername(userDto.getCredential() != null ?
                userDto.getCredential() : userDto.getPhone().toString()).cast(User.class)
                .flatMap(user -> {
                    if (user.getId() == null) {
                        return userRepository.save(userMapper.registerDtoToEntity(userDto));
                    }
                    return Mono.empty();
                })
                .map(userMapper::entityToResponseDto);
    }

    public Mono<ResponseUserDto> getUser(Object credential, String password) throws IllegalArgumentException {
        Mono<User> user;
        if (credential instanceof Long) {
            user = getUserByPhone((Long) credential);
            user.flatMap(this::updateLoginDate).subscribe();

            return convertLoginResponse(user);
        } else if (credential instanceof String) {
            password = passwordEncoder.encode(password);
            user = getUserByCredentialAndPassword((String) credential, password);
            user.flatMap(this::updateLoginDate).subscribe();

            return convertLoginResponse(user);
        }
        throw new InvalidCredentialException("Wrong input, check credentials!");
    }

    public Mono<Boolean> restore(EditUserPasswordDto passwordDto) throws IllegalArgumentException {
        passwordDto.setNewPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        return findByUsername(passwordDto.getCredential()).cast(User.class)
                .flatMap(user -> {
                    if (user.getId() != null) {
                        user.setPassword(passwordDto.getNewPassword());
                        return userRepository.save(user);
                    }
                    return Mono.empty();
                })
                .map(user -> true);
    }

    private Mono<ResponseUserDto> convertLoginResponse(Mono<User> user) {
        return user.flatMap(member -> countryRepository.findById(member.getCountryId())
                        .map(country -> {
                            member.setCountry(country);
                            return member;
                        })
                )
                .map(userMapper::entityToResponseDto);
    }

    private Mono<User> getUserByPhone(Long phone) {
        return userRepository.findByPhone(phone);
    }

    private Mono<User> getUserByCredentialAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
