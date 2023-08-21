package com.api.controllers.dto.residence;

public class CountryDto {
    private String name;

    private Short phonePreffix;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getPhonePreffix() {
        return phonePreffix;
    }

    public void setPhonePreffix(Short phonePreffix) {
        this.phonePreffix = phonePreffix;
    }
}
