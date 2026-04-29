package com.example.countrycityapi.service;

import com.example.countrycityapi.dto.CountryCreateRequest;
import com.example.countrycityapi.dto.CountryResponse;
import com.example.countrycityapi.mapper.CountryMapper;
import com.example.countrycityapi.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public CountryService(CountryRepository countryRepository, CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }

    public List<CountryResponse> getAllCountries() {
        return countryRepository.getAllCountries().stream()
                .map(countryMapper::toResponse)
                .toList();
    }

    public CountryResponse createCountry(CountryCreateRequest request) {
        var countryToSave = countryMapper.toModel(request);
        var savedCountry = countryRepository.save(countryToSave);
        return countryMapper.toResponse(savedCountry);
    }
}
