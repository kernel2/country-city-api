package com.example.countrycityapi.repository;

import com.example.countrycityapi.model.Country;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryRepository {

    private static final List<Country> COUNTRIES = List.of(
            new Country(1L, "France"),
            new Country(2L, "Germany"),
            new Country(3L, "Spain"),
            new Country(4L, "Italy")
    );

    public List<Country> getAllCountries() {
        return COUNTRIES;
    }
}
