package com.api.repositories;

import com.api.entities.residence.Country;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends R2dbcRepository <Country, Integer> {
}
