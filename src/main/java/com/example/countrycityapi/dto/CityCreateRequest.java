package com.example.countrycityapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CityCreateRequest {

    @NotBlank
    private String name;

    @NotNull
    private Long countryId;

    @NotNull
    @Min(0)
    private Long population;

    @NotBlank
    private String zipCode;

    @NotBlank
    private String description;

    public CityCreateRequest() {
    }

    public CityCreateRequest(String name, Long countryId, Long population, String zipCode, String description) {
        this.name = name;
        this.countryId = countryId;
        this.population = population;
        this.zipCode = zipCode;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getPopulation() {
        return population;
    }

    public void setPopulation(Long population) {
        this.population = population;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
