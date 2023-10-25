package com.api.controllers.dto.user;

public class EditUserProfileDto extends InitUserDto {
    private Long id;

    private Long phoneNumber;

    private String country;

    private Short phonePreffix;

    private String name;

    private String surname;

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Short getPhonePreffix() {
        return phonePreffix;
    }

    public void setPhonePreffix(Short phonePreffix) {
        this.phonePreffix = phonePreffix;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
