package com.example.countrycityapi.controller;

import com.example.countrycityapi.dto.CityCreateRequest;
import com.example.countrycityapi.dto.CityDetailsResponse;
import com.example.countrycityapi.exception.ErrorResponse;
import com.example.countrycityapi.exception.ResourceNotFoundException;
import com.example.countrycityapi.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cities")
@Tag(name = "Cities", description = "City detail endpoints")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/{cityId}")
    @Operation(summary = "Get city details by id", description = "Returns detailed information for a city.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "City retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CityDetailsResponse.class))),
            @ApiResponse(responseCode = "404", description = "City not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CityDetailsResponse> getCityById(@PathVariable Long cityId) {
        CityDetailsResponse response = cityService.getCityDetailsById(cityId);
        if (response == null) {
            throw new ResourceNotFoundException("City not found with id: " + cityId);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create a city", description = "Creates a new city in memory.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "City created successfully",
                    content = @Content(schema = @Schema(implementation = CityDetailsResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Country not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CityDetailsResponse> createCity(@Valid @RequestBody CityCreateRequest request) {
        CityDetailsResponse createdCity = cityService.createCity(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCity);
    }
}
