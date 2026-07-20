package com.delivery.countrycity.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.delivery.countrycity.exception.GlobalExceptionHandler;
import com.delivery.countrycity.service.LocationService;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LocationController.class)
@Import({LocationService.class, GlobalExceptionHandler.class})
class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listsCountries() throws Exception {
        mockMvc.perform(get("/countries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name").value("India"));
    }

    @Test
    void listsCitiesByCountryWithPagination() throws Exception {
        mockMvc.perform(get("/countries/1/cities?page=0&size=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(2))
                .andExpect(jsonPath("$.totalItems").value(4))
                .andExpect(jsonPath("$.totalPages").value(2));
    }

    @Test
    void returnsCityDetails() throws Exception {
        mockMvc.perform(get("/cities/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Bengaluru"))
                .andExpect(jsonPath("$.countryName").value("India"));
    }

    @Test
    void returnsNotFoundForUnknownCity() throws Exception {
        mockMvc.perform(get("/cities/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("City not found for id: 999"));
    }
}
