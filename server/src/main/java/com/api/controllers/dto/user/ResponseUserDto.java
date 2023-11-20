package com.api.controllers.dto.user;

import com.api.controllers.dto.residence.CountryDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.sql.Date;
import java.util.Collection;

@Setter
@Getter
public class ResponseUserDto extends InitUserDto {
    private Long id;

    private String email;

    private String name;

    private String surname;

    private Long phone;

    private Date loginDate;

    private CountryDto country;

    private String token;

    private Collection<? extends GrantedAuthority> roles;
}
