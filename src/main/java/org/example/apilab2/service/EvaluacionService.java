package org.example.apilab2.service;
import org.example.apilab2.controller.response.ImpactoProgramaResponse;
import java.time.LocalDate;
import org.example.apilab2.repository.domain.Evaluacion;

public interface EvaluacionService {
    Evaluacion crear(Long programaId, Long participanteId, LocalDate fecha, Double puntaje, String obs);
    ImpactoProgramaResponse impacto(Long programaId);
}
