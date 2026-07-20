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
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService)
    {
        this.locationService = locationService;
    }

    @GetMapping("/countries")
    public List<CountryResponse> getCountries() {

        return locationService.getCountries();
    }

    @GetMapping("/countries/{countryId}/cities")
    public PagedResponse<CityResponse> getCitiesByCountry(
            @PathVariable Long countryId,
            @Parameter(description = "Zero-based page index")
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @Parameter(description = "Number of cities per page")
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        return locationService.getCitiesByCountry(countryId, page, size);
    }

    @GetMapping("/cities/{cityId}")
    @Operation(summary = "Get city details", description = "Returns detailed information for a city.")
    public CityResponse getCityById(
            @Parameter(description = "City id")
            @PathVariable Long cityId) {
        return locationService.getCityById(cityId);
    }
}
