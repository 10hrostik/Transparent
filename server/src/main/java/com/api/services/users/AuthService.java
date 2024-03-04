package com.api.services.users;

import com.api.controllers.dto.users.EditUserPasswordDto;
import com.api.controllers.dto.users.RegisterUserDto;
import com.api.controllers.dto.users.ResponseUserDto;
import com.api.controllers.mappers.UserMapper;
import com.api.entities.attachments.UserProfileImage;
import com.api.entities.users.User;
import com.api.exceptions.UserExistsException;
import com.api.repositories.CountryRepository;
import com.api.repositories.UserRepository;
import com.api.services.attachments.MediaAttachmentProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class AuthService extends GeneralUserService implements ReactiveUserDetailsService {
  @Autowired
  private MediaAttachmentProvider<UserProfileImage> attachmentProvider;

  public AuthService(UserRepository userRepository, CountryRepository countryRepository,
                     PasswordEncoder passwordEncoder, UserMapper userMapper) {
    super(userRepository, countryRepository, passwordEncoder, userMapper);
  }

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return super.findByUsername(username);
  }

  public Mono<?> register(RegisterUserDto userDto) {
    String credential = StringUtils.firstNonBlank(userDto.getCredential(), userDto.getPhone());
    userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

    return findByUsername(credential).cast(User.class)
     .flatMap(member -> Mono.error(new UserExistsException("User already exists!")))
     .switchIfEmpty(save(userDto));
  }

  public Mono<ResponseUserDto> login(String credential, String password) {
    Mono<User> user;
    password = passwordEncoder.encode(password);
    user = userRepository.findByUsernameOrPhoneOrEmailAndPassword(credential, password);
    user.flatMap(this::updateLoginDate).subscribe();

    return convertLoginResponse(user);
  }

  public Mono<Boolean> restore(EditUserPasswordDto userDto) {
    userDto.setNewPassword(passwordEncoder.encode(userDto.getNewPassword()));
    return findByUsername(userDto.getCredential()).cast(User.class)
     .flatMap(user -> userRepository.updatePassword(userDto.getNewPassword(), user.getId()))
     .map(user -> true);
  }

  private Mono<ResponseUserDto> convertLoginResponse(Mono<User> user) {
    return user.flatMap(member -> countryRepository.findCountryById(member.getCountryId())
      .map(country -> userMapper.asUserWithCountry(member, country)))
     .switchIfEmpty(user)
     .flatMap(member -> attachmentProvider.getUserMainImage(member.getId())
      .map(image -> { member.setMainPhoto(image.getBody()); return member; }))
     .map(userMapper::asResponseDto);
  }
}
