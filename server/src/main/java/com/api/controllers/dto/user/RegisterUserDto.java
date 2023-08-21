package com.api.controllers.dto.user;

public final class RegisterUserDto extends InitUserDto {
    private Long number;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
