package com.example.countrycityapi.service;

import com.example.countrycityapi.dto.CityDetailsResponse;
import com.example.countrycityapi.dto.CitySummaryResponse;
import com.example.countrycityapi.dto.PagedResponse;
import com.example.countrycityapi.mapper.CityMapper;
import com.example.countrycityapi.model.City;
import com.example.countrycityapi.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper = new CityMapper();

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public PagedResponse<CitySummaryResponse> getCitiesByCountryId(Long countryId, int page, int size) {
        List<City> cities = cityRepository.getCitiesByCountryId(countryId);

        int safePage = Math.max(page, 0);
        int safeSize = Math.max(size, 1);
        int fromIndex = safePage * safeSize;

        if (fromIndex >= cities.size()) {
            int totalPages = calculateTotalPages(cities.size(), safeSize);
            return new PagedResponse<>(List.of(), safePage, safeSize, cities.size(), totalPages, true);
        }

        int toIndex = Math.min(fromIndex + safeSize, cities.size());
        List<CitySummaryResponse> content = cities.subList(fromIndex, toIndex).stream()
                .map(cityMapper::toSummaryResponse)
                .toList();

        int totalPages = calculateTotalPages(cities.size(), safeSize);
        boolean last = safePage >= totalPages - 1;

        return new PagedResponse<>(content, safePage, safeSize, cities.size(), totalPages, last);
    }

    public CityDetailsResponse getCityDetailsById(Long cityId) {
        return cityRepository.getCityById(cityId)
                .map(cityMapper::toDetailsResponse)
                .orElse(null);
    }

    private int calculateTotalPages(int totalElements, int size) {
        if (totalElements == 0) {
            return 0;
        }
        return (int) Math.ceil((double) totalElements / size);
    }
}
