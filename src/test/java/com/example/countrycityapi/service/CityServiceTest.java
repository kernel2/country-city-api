package com.example.countrycityapi.service;

import com.example.countrycityapi.dto.CityDetailsResponse;
import com.example.countrycityapi.dto.CitySummaryResponse;
import com.example.countrycityapi.dto.PagedResponse;
import com.example.countrycityapi.exception.ResourceNotFoundException;
import com.example.countrycityapi.repository.CityRepository;
import com.example.countrycityapi.repository.CountryRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CityServiceTest {

    private final CityService cityService = new CityService(new CityRepository(), new CountryRepository());

    @Test
    void shouldReturnCitiesByCountryWithPagination() {
        PagedResponse<CitySummaryResponse> response = cityService.getCitiesByCountryId(1L, 0, 2);

        assertNotNull(response);
        assertEquals(0, response.getPage());
        assertEquals(2, response.getSize());
        assertEquals(6, response.getTotalElements());
        assertEquals(3, response.getTotalPages());
        assertFalse(response.isLast());

        List<CitySummaryResponse> content = response.getContent();
        assertEquals(2, content.size());
        assertEquals(1L, content.get(0).getId());
        assertEquals("Paris", content.get(0).getName());
        assertEquals(1L, content.get(0).getCountryId());
    }

    @Test
    void shouldReturnCityDetailsById() {
        CityDetailsResponse response = cityService.getCityDetailsById(7L);

        assertNotNull(response);
        assertEquals(7L, response.getId());
        assertEquals("Berlin", response.getName());
        assertEquals(2L, response.getCountryId());
    }

    @Test
    void shouldReturnNullWhenCityNotFound() {
        CityDetailsResponse response = cityService.getCityDetailsById(999L);

        assertNull(response);
    }

    @Test
    void shouldThrowWhenCountryNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> cityService.getCitiesByCountryId(999L, 0, 10));
    }
}
