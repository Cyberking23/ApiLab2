package org.example.apilab2.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImpactoProgramaResponse {
    private Long programaId;
    private long totalEvaluaciones;
    private double promedio;
    private double tasaAprobacion;
}