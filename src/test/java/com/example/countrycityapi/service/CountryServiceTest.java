package com.example.countrycityapi.service;

import com.example.countrycityapi.dto.CountryCreateRequest;
import com.example.countrycityapi.dto.CountryResponse;
import com.example.countrycityapi.mapper.CountryMapper;
import com.example.countrycityapi.repository.CountryRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CountryServiceTest {

    private final CountryService countryService = new CountryService(new CountryRepository(), new CountryMapper());

    @Test
    void shouldReturnAllCountries() {
        List<CountryResponse> countries = countryService.getAllCountries();

        assertNotNull(countries);
        assertEquals(4, countries.size());
        assertEquals(1L, countries.get(0).getId());
        assertEquals("France", countries.get(0).getName());
    }

    @Test
    void shouldCreateCountry() {
        CountryCreateRequest request = new CountryCreateRequest("Portugal");

        CountryResponse created = countryService.createCountry(request);

        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("Portugal", created.getName());
    }
}
