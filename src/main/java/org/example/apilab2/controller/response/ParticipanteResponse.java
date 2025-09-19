package org.example.apilab2.controller.response;

public record ParticipanteResponse(
        Long id,
        String nombre,
        String email,
        String genero,
        String nivelEducativo,
        Boolean ingresoFormal
) {}
