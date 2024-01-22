package com.api.controllers.mappers;

import com.api.controllers.dto.users.EditUserProfileDto;
import com.api.controllers.dto.users.RegisterUserDto;
import com.api.controllers.dto.users.ResponseUserDto;
import com.api.entities.residence.Country;
import com.api.entities.users.User;
import com.api.utils.Validator;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.*;

import java.time.LocalDate;
import java.sql.Date;
import java.util.Objects;

@Mapper(componentModel = "spring", imports = { LocalDate.class, Date.class }, injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = CountryMapper.class)
public abstract class UserMapper {

    @Mapping(target = "phone", source = "user", qualifiedByName = "formatPhoneNumber")
    @Mapping(target = "roles", expression = "java(user.getAuthorities())")
    @Mapping(target = "credential", source = "username")
    public abstract ResponseUserDto entityToResponseDto(User user);

    @Mapping(target = "phone", source = "phoneNumber")
    @Mapping(target = "username", source = "credential")
    @Mapping(target = "country", ignore = true)
    public abstract User editUser(EditUserProfileDto userProfileDto, @MappingTarget User user);

    @Mapping(target = "email", source = "dto", qualifiedByName = "email")
    @Mapping(target = "username", source = "dto", qualifiedByName = "username")
    @Mapping(target = "registerDate", expression = "java(LocalDate.now())")
    @Mapping(target = "loginDate", expression = "java(LocalDate.now())")
    public abstract User registerDtoToEntity(RegisterUserDto dto);

    @Named("email")
    protected String getEmail(RegisterUserDto dto) {
        if (StringUtils.isNotBlank(dto.getCredential()) && Validator.isEmail(dto.getCredential()))
            return dto.getCredential();

        return "";
    }

    @Named("username")
    protected String getUsername(RegisterUserDto dto) {
        if (!(StringUtils.isNotBlank(dto.getCredential()) && Validator.isEmail(dto.getCredential())))
            return dto.getCredential();

        return "";
    }

    @Named("formatPhoneNumber")
    protected long formatPhoneNumber(User user) {
        Country country = user.getCountry();
        Long number = user.getPhone();

        if(Objects.nonNull(country) && Objects.nonNull(number)) {
            String stringNumber = String.valueOf(number);
            String stringPreffix = String.valueOf(country.getPhonePreffix());
            String fullNumber = stringPreffix.concat(stringNumber);

            return Long.parseLong(fullNumber);
        } else if (Objects.nonNull(number)) {
            return number;
        }

        return 0;
    }
}
