package com.delivery.countrycity.controller;

import com.delivery.countrycity.dto.CityResponse;
import com.delivery.countrycity.dto.CountryResponse;
import com.delivery.countrycity.dto.PagedResponse;
import com.delivery.countrycity.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@Tag(name = "Locations", description = "Country and city lookup APIs")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/countries")
    @Operation(summary = "List countries", description = "Returns all available countries.")
    public List<CountryResponse> getCountries() {
        return locationService.getCountries();
    }

    @GetMapping("/countries/{countryId}/cities")
    @Operation(summary = "List cities by country", description = "Returns paginated cities for the selected country.")
    public PagedResponse<CityResponse> getCitiesByCountry(
            @Parameter(description = "Country id", example = "1")
            @PathVariable Long countryId,
            @Parameter(description = "Zero-based page index", example = "0")
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Number of cities per page", example = "10")
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        return locationService.getCitiesByCountry(countryId, page, size);
    }

    @GetMapping("/cities/{cityId}")
    @Operation(summary = "Get city details", description = "Returns detailed information for a city.")
    public CityResponse getCityById(
            @Parameter(description = "City id", example = "101")
            @PathVariable Long cityId) {
        return locationService.getCityById(cityId);
    }
}
