package com.api.controllers.dto.residences;

import com.api.entities.residence.Country;

public class CountryMapper {
    public static CountryDto entityToDto(Country country) {
        CountryDto countryDto = new CountryDto();
        countryDto.setName(country.getName());
        countryDto.setPhonePreffix(country.getPhonePreffix());

        return countryDto;
    }
}
