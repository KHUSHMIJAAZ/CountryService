package com.delivery.countrycity.model;

public record City(
        Long id,
        String name,
        Long countryId,
        long population,
        String zipCode,
        String description
) {
}
