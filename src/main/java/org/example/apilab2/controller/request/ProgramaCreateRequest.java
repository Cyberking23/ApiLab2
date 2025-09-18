package org.example.apilab2.controller.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProgramaCreateRequest(
        @NotBlank String nombre,
        @NotBlank String enfoque,
        @NotBlank String duracion,
        String institucion,
        @NotNull Long consultorId
) {}
