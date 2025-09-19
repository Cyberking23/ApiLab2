package org.example.apilab2.controller.response;

public record ProgramaResponse(
        Long id,
        String nombre,
        String enfoque,
        String duracion,
        String institucion,
        Long consultorId
) {}
