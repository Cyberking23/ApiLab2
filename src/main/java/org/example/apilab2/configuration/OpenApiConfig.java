package org.example.apilab2.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
        @Bean
        public OpenAPI api() {
                return new OpenAPI().info(new Info().title("API Lab 2 - Brecha Digital").version("v1")
                        .description("API con estructura clásica (configuration/controller/repository/service) y MySQL"));
        }
}
