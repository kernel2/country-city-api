package com.example.countrycityapi.service;

import com.example.countrycityapi.dto.CountryResponse;
import com.example.countrycityapi.mapper.CountryMapper;
import com.example.countrycityapi.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper = new CountryMapper();

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<CountryResponse> getAllCountries() {
        return countryRepository.getAllCountries().stream()
                .map(countryMapper::toResponse)
                .toList();
    }
}
