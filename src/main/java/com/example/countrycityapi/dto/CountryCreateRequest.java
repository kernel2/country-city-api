package com.example.countrycityapi.dto;

import jakarta.validation.constraints.NotBlank;

public class CountryCreateRequest {

    @NotBlank
    private String name;

    public CountryCreateRequest() {
    }

    public CountryCreateRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
