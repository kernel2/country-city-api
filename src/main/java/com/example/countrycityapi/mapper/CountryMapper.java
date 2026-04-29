package com.example.countrycityapi.mapper;

import com.example.countrycityapi.dto.CountryCreateRequest;
import com.example.countrycityapi.dto.CountryResponse;
import com.example.countrycityapi.model.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {

    public Country toModel(CountryCreateRequest request) {
        if (request == null) {
            return null;
        }
        return new Country(
                null,
                request.getName()
        );
    }

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
