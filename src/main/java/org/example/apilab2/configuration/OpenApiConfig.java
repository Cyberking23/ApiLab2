package org.example.apilab2.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "API Programas y Evaluaciones",
                version = "v1",
                description = "Gestión de consultores, programas, participantes y evaluaciones.",
                contact = @Contact(name = "Equipo Backend", email = "backend@lospukis.com"),
                license = @License(name = "MIT")
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local"),
                @Server(url = "https://api.tu-dominio.com", description = "Prod")
        }
)
@Configuration
public class OpenApiConfig {}