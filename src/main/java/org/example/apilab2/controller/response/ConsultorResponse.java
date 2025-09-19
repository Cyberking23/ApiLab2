package org.example.apilab2.controller.response;

public record ConsultorResponse(
        Long id,
        String nombre,
        String especializacion,
        Boolean inActivoActualizado
) {}
