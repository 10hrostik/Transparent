package com.api.controllers.dto.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserProfileDto extends InitUserDto {
    private Long id;

    private Long phoneNumber;

    private String country;

    private Short phonePreffix;

    private String name;

    private String surname;
}
