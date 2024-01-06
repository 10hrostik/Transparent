package com.api.controllers.dto.users;

import com.api.controllers.dto.residences.CountryMapper;
import com.api.entities.users.User;
import com.api.utils.Validator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class UserMapper {
    public static ResponseUserDto entityToResponseDto(User user) {
        ResponseUserDto responseUserDto = new ResponseUserDto();
        responseUserDto.setId(user.getId());
        responseUserDto.setEmail(user.getEmail());
        responseUserDto.setCredential(user.getUsername());
        responseUserDto.setName(user.getName());
        responseUserDto.setSurname(user.getSurname());
        responseUserDto.setPhone(user.getPhone());
        responseUserDto.setRoles(user.getAuthorities());

        if (Objects.nonNull(user.getLoginDate()))
            responseUserDto.setLoginDate(Date.valueOf(user.getLoginDate()));
        if (user.getCountry() != null)
            responseUserDto.setCountry(CountryMapper.entityToDto(user.getCountry()));

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
}
