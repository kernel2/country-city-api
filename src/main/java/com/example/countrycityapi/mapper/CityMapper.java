package com.example.countrycityapi.mapper;

import com.example.countrycityapi.dto.CityDetailsResponse;
import com.example.countrycityapi.dto.CitySummaryResponse;
import com.example.countrycityapi.model.City;

public class CityMapper {

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
