package com.delivery.countrycity.dto;

public record CityResponse(
        Long id,
        String name,
        Long countryId,
        String countryName,
        Long population,
        String zipCode,
        String description
) {
}
