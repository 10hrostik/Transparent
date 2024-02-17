package com.api.controllers.dto.users;

import com.api.controllers.dto.residences.CountryDto;
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

    private String description;

    private CountryDto country;

    private String token;

    private Collection<? extends GrantedAuthority> roles;
}
