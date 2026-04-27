package com.example.countrycityapi.repository;

import com.example.countrycityapi.model.Country;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CountryRepository {

    private final List<Country> countries = new ArrayList<>(List.of(
            new Country(1L, "France"),
            new Country(2L, "Germany"),
            new Country(3L, "Spain"),
            new Country(4L, "Italy")
    ));
    private final AtomicLong nextId = new AtomicLong(5L);

    public List<Country> getAllCountries() {
        return List.copyOf(countries);
    }

    public boolean existsById(Long countryId) {
        return countries.stream().anyMatch(country -> country.getId().equals(countryId));
    }

    public Country save(Country country) {
        Country savedCountry = new Country(nextId.getAndIncrement(), country.getName());
        countries.add(savedCountry);
        return savedCountry;
    }
}
