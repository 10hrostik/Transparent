package com.api.controllers.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class RegisterUserDto extends InitUserDto {
    private Long number;
}
