package com.api.controllers.dto.users;


import lombok.Data;

@Data
public class InitUserDto {
    private String credential;

    private String password;
}
