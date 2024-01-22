package com.api.controllers.mappers;

import com.api.controllers.dto.residences.CountryDto;
import com.api.entities.residence.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    CountryDto entityToDto(Country country);
}
