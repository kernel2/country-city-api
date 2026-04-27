package com.example.countrycityapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI countryCityApiOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Country City API")
                        .version("v1")
                        .description("REST API for countries and cities exploration with pagination support.")
                        .contact(new Contact().name("API Team"))
                        .license(new License().name("Internal Use")));
    }
}
