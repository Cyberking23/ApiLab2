package org.example.apilab2.controller.response;

import java.time.LocalDate;

public record EvaluacionResponse(
        Long id,
        LocalDate fecha,
        Double puntaje,
        String observaciones,
        ParticipanteResponse participante,
        ProgramaResponse programa
) {}
