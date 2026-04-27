package com.example.countrycityapi.controller;

import com.example.countrycityapi.dto.CountryResponse;
import com.example.countrycityapi.dto.PagedResponse;
import com.example.countrycityapi.dto.CitySummaryResponse;
import com.example.countrycityapi.exception.ErrorResponse;
import com.example.countrycityapi.service.CityService;
import com.example.countrycityapi.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/countries")
@Tag(name = "Countries", description = "Country and country-related city endpoints")
public class CountryController {

    private final CountryService countryService;
    private final CityService cityService;

    public CountryController(CountryService countryService, CityService cityService) {
        this.countryService = countryService;
        this.cityService = cityService;
    }

    @GetMapping
    @Operation(summary = "List all countries", description = "Returns the full list of available countries.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Countries retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CountryResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Unexpected server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<CountryResponse>> getCountries() {
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    @GetMapping("/{countryId}/cities")
    @Operation(
            summary = "List cities by country with pagination",
            description = "Returns paginated cities for a given country using page and size query parameters."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cities retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PagedResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Country not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<PagedResponse<CitySummaryResponse>> getCitiesByCountryId(
            @PathVariable Long countryId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) int size
    ) {
        return ResponseEntity.ok(cityService.getCitiesByCountryId(countryId, page, size));
    }
}
