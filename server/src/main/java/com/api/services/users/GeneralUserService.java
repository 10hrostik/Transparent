package com.api.services.users;

import com.api.controllers.dto.users.RegisterUserDto;
import com.api.controllers.dto.users.ResponseUserDto;
import com.api.controllers.mappers.UserMapper;
import com.api.entities.users.User;
import com.api.repositories.CountryRepository;
import com.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public abstract class GeneralUserService {

  protected final UserRepository userRepository;

  protected final CountryRepository countryRepository;

  protected final PasswordEncoder passwordEncoder;

  protected final UserMapper userMapper;

  public Mono<UserDetails> findByUsername(String username) {
    return userRepository.findByUsernameOrPhoneOrEmail(username).cast(UserDetails.class);
  }

  protected Mono<ResponseUserDto> save(RegisterUserDto registerUserDto) {
    return userRepository.save(userMapper.asRegisteredUser(registerUserDto)).map(userMapper::asResponseDto);
  }

  protected Mono<Void> updateLoginDate(User user) {
    user.setLoginDate(LocalDate.now());
    return userRepository.save(user).then();
  }
}
