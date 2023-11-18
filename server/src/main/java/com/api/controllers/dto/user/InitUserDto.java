package com.api.controllers.dto.user;


import lombok.Data;

@Data
public class InitUserDto {
    private String credential;

    private String password;
}
