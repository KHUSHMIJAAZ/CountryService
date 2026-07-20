package com.delivery.countrycity.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI countryCityOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Country City API")
                        .version("1.0.0")
                        .description("REST API for listing countries, listing paginated cities by country, and retrieving city details.")
                        .contact(new Contact().name("OrderDelivery")));
    }
}
