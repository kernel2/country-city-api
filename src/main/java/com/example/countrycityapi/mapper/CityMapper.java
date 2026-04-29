package com.example.countrycityapi.mapper;

import com.example.countrycityapi.dto.CityCreateRequest;
import com.example.countrycityapi.dto.CityDetailsResponse;
import com.example.countrycityapi.dto.CitySummaryResponse;
import com.example.countrycityapi.model.City;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {

    public City toModel(CityCreateRequest request) {
        if (request == null) {
            return null;
        }
        return new City(
                null,
                request.getName(),
                request.getCountryId(),
                request.getPopulation(),
                request.getZipCode(),
                request.getDescription()
        );
    }

    public CitySummaryResponse toSummaryResponse(City city) {
        if (city == null) {
            return null;
        }
        return new CitySummaryResponse(
                city.getId(),
                city.getName(),
                city.getCountryId()
        );
    }

    public CityDetailsResponse toDetailsResponse(City city) {
        if (city == null) {
            return null;
        }
        return new CityDetailsResponse(
                city.getId(),
                city.getName(),
                city.getCountryId(),
                city.getPopulation(),
                city.getZipCode(),
                city.getDescription()
        );
    }
}
