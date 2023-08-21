package com.api.controllers.dto.user;

import com.api.controllers.dto.residence.CountryDto;

import java.sql.Date;

public class ResponseUserDto extends InitUserDto {
    private Long id;

    private String email;

    private String name;

    private String surname;

    private Long phone;

    private Date loginDate;

    private CountryDto country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public CountryDto getCountry() {
        return country;
    }

    public void setCountry(CountryDto country) {
        this.country = country;
    }
}
