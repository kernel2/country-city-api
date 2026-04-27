package com.example.countrycityapi.dto;

import java.util.Objects;

public class CityDetailsResponse {

    private Long id;
    private String name;
    private Long countryId;
    private Long population;
    private String zipCode;
    private String description;

    public CityDetailsResponse() {
    }

    public CityDetailsResponse(Long id, String name, Long countryId, Long population, String zipCode, String description) {
        this.id = id;
        this.name = name;
        this.countryId = countryId;
        this.population = population;
        this.zipCode = zipCode;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CityDetailsResponse that)) {
            return false;
        }
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(countryId, that.countryId)
                && Objects.equals(population, that.population)
                && Objects.equals(zipCode, that.zipCode)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, countryId, population, zipCode, description);
    }

    @Override
    public String toString() {
        return "CityDetailsResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countryId=" + countryId +
                ", population=" + population +
                ", zipCode='" + zipCode + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
