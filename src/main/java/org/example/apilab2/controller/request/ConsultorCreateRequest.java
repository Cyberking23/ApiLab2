package org.example.apilab2.controller.request;
import jakarta.validation.constraints.NotBlank;

public record ConsultorCreateRequest(
        @NotBlank String nombre,
        @NotBlank String especializacion,
        Boolean inActivoActualizado
) {}
