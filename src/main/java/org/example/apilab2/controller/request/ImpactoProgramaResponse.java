package org.example.apilab2.controller.request;

public record ImpactoProgramaResponse(
        Long programaId,
        long totalEvaluaciones,
        double promedioPuntaje,
        double tasaAprobacion
) {}
