package com.example.countrycityapi.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
