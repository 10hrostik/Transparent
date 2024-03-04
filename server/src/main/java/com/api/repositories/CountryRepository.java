package com.api.repositories;

import com.api.entities.residence.Country;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CountryRepository extends R2dbcRepository<Country, Long> {
  Mono<Country> findCountryById(Long id);
}
