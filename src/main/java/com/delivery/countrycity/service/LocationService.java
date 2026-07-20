package com.delivery.countrycity.service;

import com.delivery.countrycity.dto.CityResponse;
import com.delivery.countrycity.dto.CountryResponse;
import com.delivery.countrycity.dto.PagedResponse;
import com.delivery.countrycity.exception.ResourceNotFoundException;
import com.delivery.countrycity.model.City;
import com.delivery.countrycity.model.Country;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private final List<Country> countries = List.of(
            new Country(1L, "India"),
            new Country(2L, "United States"),
            new Country(3L, "United Kingdom")
    );

    private final List<City> cities = List.of(
            new City(101L, "Bengaluru", 1L, 13_600_000L, "560001", "Technology hub known for startups, parks, and a strong engineering talent pool."),
            new City(102L, "Mumbai", 1L, 21_300_000L, "400001", "Major financial and entertainment center on India's west coast."),
            new City(103L, "Delhi", 1L, 32_900_000L, "110001", "National capital region with historic landmarks and government institutions."),
            new City(104L, "Hyderabad", 1L, 10_800_000L, "500001", "Growing technology and pharmaceutical center with a rich food culture."),
            new City(201L, "New York", 2L, 18_800_000L, "10001", "Global finance, media, and culture center with dense transit connectivity."),
            new City(202L, "San Francisco", 2L, 3_300_000L, "94102", "Bay Area city known for technology companies, hills, and waterfront neighborhoods."),
            new City(203L, "Chicago", 2L, 9_400_000L, "60601", "Midwestern business hub known for architecture, logistics, and Lake Michigan."),
            new City(204L, "Austin", 2L, 2_400_000L, "73301", "Fast-growing technology and music center in Texas."),
            new City(301L, "London", 3L, 14_800_000L, "SW1A", "Capital city and international business center on the River Thames."),
            new City(302L, "Manchester", 3L, 2_800_000L, "M1", "Northern England city known for industry, universities, music, and sports."),
            new City(303L, "Birmingham", 3L, 2_600_000L, "B1", "Large Midlands city with manufacturing, commerce, and canal heritage.")
    );

    private final Map<Long, Country> countriesById = countries.stream()
            .collect(Collectors.toUnmodifiableMap(Country::id, Function.identity()));

    public List<CountryResponse> getCountries() {
        return countries.stream()
                .sorted(Comparator.comparing(Country::name))
                .map(country -> new CountryResponse(country.id(), country.name()))
                .toList();
    }

    public PagedResponse<CityResponse> getCitiesByCountry(Long countryId, int page, int size) {
        Country country = getCountryOrThrow(countryId);
        List<CityResponse> matchingCities = cities.stream()
                .filter(city -> city.countryId().equals(country.id()))
                .sorted(Comparator.comparing(City::name))
                .map(city -> toCityResponse(city, country))
                .toList();

        int totalItems = matchingCities.size();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        int fromIndex = Math.min(page * size, totalItems);
        int toIndex = Math.min(fromIndex + size, totalItems);
        List<CityResponse> pageContent = matchingCities.subList(fromIndex, toIndex);

        return new PagedResponse<>(
                pageContent,
                page,
                size,
                totalItems,
                totalPages,
                page == 0,
                totalPages == 0 || page >= totalPages - 1
        );
    }

    public CityResponse getCityById(Long cityId) {
        City city = cities.stream()
                .filter(candidate -> candidate.id().equals(cityId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("City not found for id: " + cityId));
        return toCityResponse(city, getCountryOrThrow(city.countryId()));
    }

    private Country getCountryOrThrow(Long countryId) {
        Country country = countriesById.get(countryId);
        if (country == null) {
            throw new ResourceNotFoundException("Country not found for id: " + countryId);
        }
        return country;
    }

    private CityResponse toCityResponse(City city, Country country) {
        return new CityResponse(
                city.id(),
                city.name(),
                city.countryId(),
                country.name(),
                city.population(),
                city.zipCode(),
                city.description()
        );
    }
}
