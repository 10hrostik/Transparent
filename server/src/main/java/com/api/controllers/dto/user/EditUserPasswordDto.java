package com.api.controllers.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditUserPasswordDto extends InitUserDto {
    private String newPassword;
}
