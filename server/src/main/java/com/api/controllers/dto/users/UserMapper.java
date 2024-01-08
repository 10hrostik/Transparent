package com.api.controllers.dto.users;

import com.api.controllers.dto.residences.CountryDto;
import com.api.controllers.dto.residences.CountryMapper;
import com.api.entities.users.User;
import com.api.utils.Validator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class UserMapper {
    public static ResponseUserDto entityToResponseDto(User user) {
        ResponseUserDto responseUserDto = new ResponseUserDto();
        CountryDto country = null;
        Long userPhone = user.getPhone();

        responseUserDto.setId(user.getId());
        responseUserDto.setEmail(user.getEmail());
        responseUserDto.setCredential(user.getUsername());
        responseUserDto.setName(user.getName());
        responseUserDto.setSurname(user.getSurname());
        responseUserDto.setRoles(user.getAuthorities());

        if (user.getCountry() != null) {
            country = CountryMapper.entityToDto(user.getCountry());
            responseUserDto.setCountry(country);
        }
        if (Objects.nonNull(user.getLoginDate()))
            responseUserDto.setLoginDate(Date.valueOf(user.getLoginDate()));
        if(Objects.nonNull(country) && Objects.nonNull(userPhone))
            responseUserDto.setPhone(formatPhoneNumber(userPhone, country.getPhonePreffix()));

        return responseUserDto;
    }

    public static User registerDtoToEntity(RegisterUserDto dto) {
        User user = new User();
        user.setPassword(dto.getPassword());
        user.setPhone(dto.getNumber());
        user.setRegisterDate(LocalDate.now());
        user.setLoginDate(LocalDate.now());
        if (dto.getCredential() != null && Validator.isEmail(dto.getCredential()))
            user.setEmail(dto.getCredential());
        else
            user.setUsername(dto.getCredential());

        return user;
    }

    public static User editUser(EditUserProfileDto userProfileDto, User user) {
        user.setName(userProfileDto.getName());
        user.setSurname(userProfileDto.getSurname());
        user.setPhone(userProfileDto.getPhoneNumber());
        user.setUsername(userProfileDto.getCredential());

        return user;
    }

    private static long formatPhoneNumber(long number, short preffix) {
        String stringNumber = String.valueOf(number);
        String stringPreffix = String.valueOf(preffix);
        String fullNumber = stringPreffix.concat(stringNumber);

        return Long.parseLong(fullNumber);
    }
}
