package com.example.countrycityapi.mapper;

import com.example.countrycityapi.dto.CountryResponse;
import com.example.countrycityapi.model.Country;

public class CountryMapper {

    public CountryResponse toResponse(Country country) {
        if (country == null) {
            return null;
        }
        return new CountryResponse(
                country.getId(),
                country.getName()
        );
    }
}
