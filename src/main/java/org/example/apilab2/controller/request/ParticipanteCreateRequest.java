package org.example.apilab2.controller.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ParticipanteCreateRequest(
        @NotBlank String nombre,
        @Email @NotBlank String email,
        @NotBlank String genero,
        String nivelEducativo,
        Boolean ingresoFormal
) {}
